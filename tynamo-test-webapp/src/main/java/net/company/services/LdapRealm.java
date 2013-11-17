package net.company.services;

import org.apache.shiro.authz.*;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.realm.ldap.JndiLdapRealm;
import org.apache.shiro.realm.ldap.LdapContextFactory;
import org.apache.shiro.realm.ldap.LdapUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.PermissionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.AuthenticationException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapContext;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * LDAP {@link JndiLdapRealm} implementation that supports authorization with role / permission mapping.
 * Authorization cache is enabled.
 */
@SuppressWarnings("UnusedDeclaration")
public class LdapRealm extends JndiLdapRealm {
    private final static Logger logger = LoggerFactory.getLogger(LdapRealm.class);

    private String searchBase;
    private String searchFilter;
    private String searchGroupObjectClass;
    private String searchUniqueMemberAttribute;
    private String searchUniqueMemberAttributeValueTemplate;
    private String userGroupAttribute;
    private String groupNameAttribute;

    private Map<String, String> groupRolesMap;

    public LdapRealm(){
        super();
        onInit();
        this.setAuthenticationCachingEnabled(false);
        this.setCacheManager(new MemoryConstrainedCacheManager());
        this.setAuthorizationCachingEnabled(true);
    }

    /**
     * Get groups from LDAP for a specific user and enable Shiro Roles and Permissions
     *
     * @param principals the principals of the Subject whose AuthenticationInfo should be queried from the LDAP server.
     * @param ldapContextFactory factory used to retrieve LDAP connections.
     * @return an {@link AuthorizationInfo} instance containing information retrieved from the LDAP server.
     * @throws NamingException if any LDAP errors occur during the search.
     */
    @Override
    protected AuthorizationInfo queryForAuthorizationInfo(PrincipalCollection principals,
                                                          LdapContextFactory ldapContextFactory) throws NamingException {
        Set<String> roleNames = new HashSet<String>();
        String username = (String) getAvailablePrincipal(principals);
        LdapContext systemLdapContext = null;

        try {
            systemLdapContext = ldapContextFactory.getSystemLdapContext();
            SearchControls constraints = new SearchControls();
            constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);

            NamingEnumeration<?> answer = systemLdapContext.search(searchBase, "userd=" + username, constraints);

            // todo make it more robust only one userd
            String role = null;
            while (answer.hasMore()) {
                role = getRole((SearchResult) answer.next());
                if (role!=null) {
                    roleNames.add(role);
                } else {
                    String msg = "Bad LDAP groups (lgrp), no role found for user:" + username;
                    logger.error(msg);
                    // todo add to audit
                    throw new AuthorizationException(msg);
                }
            }

            logger.info("Role for {}: {}", username, roleNames);  // todo enable as audit

            SimpleAuthorizationInfo sa = new SimpleAuthorizationInfo(roleNames);
            if (role!=null) {
                String[] perms = rolesPermissions.get(role);
                if (perms!=null) {
                    sa.setStringPermissions(new HashSet<String>(Arrays.asList(perms)));
                }
            }
            return sa;
        } catch (AuthenticationException e) {
            String msg = "Failed to authenticate on LDAP for " + username;
            logger.warn(msg);
            // do nothing as the principal was not authenticated on LDAP
            throw new AuthenticationException(msg);
        } finally {
            LdapUtils.closeContext(systemLdapContext);
        }
    }

    private boolean contains(String str, String[] filter){
        for(String t : filter) {
            if (str.equals(t)) return true;
        }
        return false;
    }

    private String[] trim(String[] filter){
        for(int i = 0; i < filter.length-1; i++) {
            filter[i] = filter[i].trim();
        }
        return filter;
    }

    // set first Role for a corresponding ldapGroup
    private String getRole(SearchResult sr) throws NamingException {
        try {
            Attribute attr = sr.getAttributes().get(groupNameAttribute);
            for (NamingEnumeration attributesEnum = attr.getAll(); attributesEnum.hasMore(); ) {
                String group = (String) attributesEnum.next();
                for (String ldapGroup : groupRolesMap.keySet()) {
                    if (group.equals(ldapGroup)) {
                        return groupRolesMap.get(ldapGroup);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("", e);
        }
        return null;
    }

    public void setSearchBase(String searchBase) {
        this.searchBase = searchBase;
    }

    public void setUserGroupAttribute(String userGroupAttribute) {
        this.userGroupAttribute = userGroupAttribute;
    }

    public void setGroupNameAttribute(String groupNameAttribute) {
        this.groupNameAttribute = groupNameAttribute;
    }

    public void setSearchFilter(String searchFilter) {
        this.searchFilter = searchFilter;
    }

    public void setSearchGroupObjectClass(String searchGroupObjectClass) {
        this.searchGroupObjectClass = searchGroupObjectClass;
    }

    public void setSearchUniqueMemberAttribute(String searchUniqueMemberAttribute) {
        this.searchUniqueMemberAttribute = searchUniqueMemberAttribute;
    }

    public void setSearchUniqueMemberAttributeValueTemplate(String searchUniqueMemberAttributeValueTemplate) {
        this.searchUniqueMemberAttributeValueTemplate = searchUniqueMemberAttributeValueTemplate;
    }

    /**
     * Set mapping between LDAP groups and application roles
     *
     * @param groupRolesMap
     */

    public void setGroupRolesMap(Map<String, String> groupRolesMap) {
        this.groupRolesMap = groupRolesMap;
    }

    // http://shiro.apache.org/static/1.2.2/xref/org/apache/shiro/realm/SimpleAccountRealm.html
    // http://shiro.apache.org/static/1.2.2/xref/org/apache/shiro/realm/text/TextConfigurationRealm.html
    private static Map<String, String[]> rolesPermissions;

    public void processRoleDefinitions(Map<String, String[]> roleDefs) {

        if (roleDefs == null || roleDefs.isEmpty()) {
            return;
        }

        rolesPermissions = roleDefs;

        for (String roleName : rolesPermissions.keySet()) {
            String[] permissions = roleDefs.get(roleName);
            SimpleRole role = getRole(roleName);
            if (role == null) {
                role = new SimpleRole(roleName);
                add(role);
            }

            for (String permission: permissions) {
                Set<Permission> perms = PermissionUtils.resolveDelimitedPermissions(permission, getPermissionResolver());
                role.setPermissions(perms);
            }
        }
    }

    protected final Map<String, SimpleRole> roles = new HashMap<String, SimpleRole>(); //roleName-to-SimpleRole
    protected final ReadWriteLock ROLES_LOCK = new ReentrantReadWriteLock();

    protected SimpleRole getRole(String rolename) {
        ROLES_LOCK.readLock().lock();
        try {
            return roles.get(rolename);
        } finally {
            ROLES_LOCK.readLock().unlock();
        }
    }

    protected void add(SimpleRole role) {
        ROLES_LOCK.writeLock().lock();
        try {
            roles.put(role.getName(), role);
        } finally {
            ROLES_LOCK.writeLock().unlock();
        }
    }

    //
    //boolean created;
   /* @Override
    public void setCacheManager(CacheManager authzInfoCacheManager)
    {
        if (created && getCacheManager() != null)
        {
            return;
        }
        super.setCacheManager(authzInfoCacheManager);
    }  */

    /**
     * Remove initialization after installing cacheManager.
     * This created problems of premature initialization,
     * when not specified the name of realm, respectively
     *
     * @see org.apache.shiro.realm.AuthorizingRealm#afterCacheManagerSet()
     */
   /* @Override
    protected void afterCacheManagerSet()
    {
        if (created)
        {
            super.afterCacheManagerSet();
        } else {
            setAuthorizationCache(null);
        }
    }*/

   // http://shiro.apache.org/static/current/apidocs/org/apache/shiro/realm/AuthorizingRealm.html#clearCachedAuthorizationInfo%28org.apache.shiro.subject.PrincipalCollection%29
   /* public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
           super.clearCachedAuthorizationInfo(principals);
    }   */
}
