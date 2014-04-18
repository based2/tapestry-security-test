package net.company.services;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityService;
import org.tynamo.security.services.impl.SecurityFilterChain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by based on 18/04/14.
 * https://issues.apache.org/jira/browse/TAP5-2281
 */
public class NavbarAccessImpl implements NavbarAccess
{
    private final static Logger LOG = LoggerFactory.getLogger(AppModule.class);

    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_SELLER = "seller";
    public static final String ROLE_EDITOR = "editor";
    public static final String ROLE_ADMIN = "admin";

    public static final String T5_DASHBOARD = "T5Dashboard";
    public static final String DEV = "DEV$!|#@";

    private static String[][] LINK_PATH_PERMISSIONS = new String[][]{
            {T5_DASHBOARD, T5_DASHBOARD, DEV, "dashboard"}, // used only when isProduction = false: only in dev mode
            {"Board", "/board/**", AppModule.PERMISSION_CUSTOMER, "glass"},
            {"Stats", "/stats/**",  AppModule.PERMISSION_SELLER, "eye"},
            {"Inventory", "/inventory/**",  AppModule.PERMISSION_EDITOR, "compass"},
            {"Controls", "/controls/**",  AppModule.PERMISSION_EDITOR, "flash"},
            {"Admin", "/admin/**",  AppModule.PERMISSION_ADMIN, "gavel"},
            {"Bootswatch", DEV}, // used only when isProduction = false, only in dev mode
            {"About"},
            {"Contact"}
    };

    public String[][] getPermissions(){
        return LINK_PATH_PERMISSIONS;
    }

    @Override
    public void setupSecurity(Configuration<SecurityFilterChain> configuration,
                                                       SecurityFilterChainFactory factory, WebSecurityManager securityManager)
    {
        configuration.add(factory.createChain(AppModule.URL_LOGIN).add(factory.anon()).build());
        configuration.add(factory.createChain(AppModule.URL_SUCCESS).add(factory.user()).build());
        configuration.add(factory.createChain(AppModule.URL_UNAUTHORIZED).add(factory.user()).build());

        for (String[] linkPathPermission : LINK_PATH_PERMISSIONS) {
            try {
                if (linkPathPermission.length > 2) {
                    LOG.info("Permission " + linkPathPermission[0] + " > " + linkPathPermission[1] + " > " + linkPathPermission[2]);
                    configuration.add(factory.createChain(linkPathPermission[1]).add(factory.perms(), linkPathPermission[2]).build());
                } else {
                    LOG.info("Public " + linkPathPermission[0]);
                }
            } catch (Exception e) {
                LOG.debug("", e);
            }
        }
    }

    public Map<String, String> getPageNames(SecurityService securityService)
    {
       Map<String, String> pageIcons = new LinkedHashMap<String, String>();
       for (String[] pageOrDir : this.getPermissions()) {
           if (pageOrDir != null) {
               try {
                   if (pageOrDir.length > 3) {
                       if (securityService.hasPermission(pageOrDir[2])) {
                           load(pageIcons, pageOrDir);
                       }
                   } else {
                       load(pageIcons, pageOrDir);
                   }
               } catch (Exception e) {
                   try {
                       load(pageIcons, pageOrDir);
                   } catch (Exception e2) {
                       try {
                           LOG.error("Failed to proceed to register pageOrDir " + pageOrDir[0]
                                   + " for permission " + pageOrDir[2]);
                       } catch (Exception e1) {
                           LOG.error("Failed to proceed to register pageOrDir " + pageOrDir[0]);
                       }
                       if (!AppModule.isProduction)
                           LOG.error("", e);
                   }
               }
           }
       }
       return pageIcons;
       //return new ArrayList<String>(pageIcons.keySet());
    }

    private void load(Map<String, String> pageIcons, String pageName, String iconName)
    {
        if (iconName==null) iconName = "";
        pageIcons.put(pageName, iconName);
    }

    private void load(Map<String, String> pageIcons, String[] pageOrDir)
    {
        if (pageOrDir.length == 1) {
            load(pageIcons, pageOrDir[0], "");
        } else if ((NavbarAccessImpl.DEV.equals(pageOrDir[1]))||(NavbarAccessImpl.DEV.equals(pageOrDir[2]))) {
            // Add specific page enabled by DEV mode
            if (!AppModule.isProduction) {
                load(pageIcons, pageOrDir[0], pageOrDir[1]);
            }
        } else if (pageOrDir.length==4) {
            load(pageIcons, pageOrDir[0], pageOrDir[3]);
        } else {
            load(pageIcons, pageOrDir[0], "");
        }
    }

}
