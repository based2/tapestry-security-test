package net.company.services;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.MetaDataConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.RequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.Security;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityModule;
import org.tynamo.security.services.impl.SecurityFilterChain;
import org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter;
import org.tynamo.shiro.extension.realm.text.ExtendedPropertiesRealm;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule({SecurityModule.class})
public class AppModule
{
    private final static Logger LOG = LoggerFactory.getLogger(AppModule.class);

    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_SELLER = "seller";
    public static final String ROLE_EDITOR = "editor";
    public static final String ROLE_ADMIN = "admin";

    public static final String PERMISSION_CUSTOMER = "customerPermission:1";
    public static final String PERMISSION_SELLER = "sellerPermission:1";
    public static final String PERMISSION_EDITOR = "editorPermission:1";
    public static final String PERMISSION_ADMIN = "adminPermission:1";

    public static final String URL_LOGIN = "/index";
    public static final String URL_SUCCESS = "/board/Index";//"/success";
    public static final String URL_UNAUTHORIZED = "/AccessDenied";

    public static final String T5_DASHBOARD = "T5Dashboard";
    public static final String DEV = "DEV$!|#@";

    public static String[][] LINK_PATH_PERMISSIONS = new String[][]{
            {T5_DASHBOARD, DEV}, // used only when isProduction = false, only in dev mode
            {"Board","/board/**",PERMISSION_CUSTOMER},
            {"Stats","/stats/**",PERMISSION_SELLER},
            {"Inventory","/inventory/**",PERMISSION_EDITOR},
            {"Controls","/controls/**",PERMISSION_EDITOR},
            {"Admin","/admin/**",PERMISSION_ADMIN},
            {"Bootswatch", DEV}, // used only when isProduction = false, only in dev mode
            {"About"},
            {"Contact"}
    };

    public static boolean isProduction = true;

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false

        if (!isProduction) {
            configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
            configuration.add(SymbolConstants.COMPONENT_RENDER_TRACING_ENABLED, "false");
            configuration.add(SymbolConstants.COMPACT_JSON, "false");
            configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
            configuration.add(SymbolConstants.MINIFICATION_ENABLED, "false");
        } else {
            LINK_PATH_PERMISSIONS[0][0] = null;
            configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
            configuration.add(SymbolConstants.COMPONENT_RENDER_TRACING_ENABLED, "false");
            configuration.add(SymbolConstants.COMPACT_JSON, "true");
            configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "true");
            configuration.add(SymbolConstants.MINIFICATION_ENABLED, "true");
        }

        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");

        configuration.add(SymbolConstants.SESSION_LOCKING_ENABLED, "true");

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

        //configuration.add(SymbolConstants.DEFAULT_STYLESHEET, "context:styles/empty.css");
        // todo check http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/HMAC-Passphrase-Could-Be-Much-More-Useful-Correct-Me-If-I-m-Wrong-td5724606.html
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, RandomStringUtils.randomAscii(10));

        // The application version number is incorporated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions.
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");

        configuration.add(SymbolConstants.START_PAGE_NAME, "SEC");

        configuration.add(SymbolConstants.SECURE_ENABLED, "true");

        configuration.add(SymbolConstants.HOSTPORT, "8080");
        configuration.add(SymbolConstants.HOSTPORT_SECURE, "8443");

        // Tynamo's tapestry-security module configuration
        configuration.add(SecuritySymbols.LOGIN_URL, URL_LOGIN);
        configuration.add(SecuritySymbols.SUCCESS_URL, URL_SUCCESS);
        configuration.add(SecuritySymbols.UNAUTHORIZED_URL, URL_UNAUTHORIZED);

        configuration.add(SymbolConstants.TAPESTRY_VERSION, "false");
    }

    public static void bind(final ServiceBinder binder)
    {
        binder.bind(SecurityFilterChainFactory.class, RedirectHTTP401Error.class).withId("RedirectHTTP401Error");
        //binder.bind(ObjectFactory.class, ObjectFactoryImpl.class);
        //binder.bind(CustomerManager.class, CustomerManagerImpl.class);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Security - Tynamo/Shiro

    public void contributeMetaDataLocator(MappedConfiguration<String,String> configuration)
    {
        // Enabling @Secure only on some pages (the remaining ones are insecure)
        configuration.add(MetaDataConstants.SECURE_PAGE, "true");
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
        configuration.add(new ExtendedPropertiesRealm("classpath:shiro-users.properties"));
    }

    @Contribute(ServiceOverride.class) // binder.bind(SecurityFilterChainFactory.class, RedirectHTTP401Error.class).withId("RedirectHTTP401Error");
    public static void overrideSecurityFilterChainFactory(MappedConfiguration<Class<?>,Object> configuration,
                                                          @Local SecurityFilterChainFactory securityFilterChainFactory)
    {
        configuration.add(SecurityFilterChainFactory.class, securityFilterChainFactory);
    }

    @Contribute(HttpServletRequestFilter.class)
    @Marker(Security.class)
    public static void setupSecurity(Configuration<SecurityFilterChain> configuration,
                                     SecurityFilterChainFactory factory, WebSecurityManager securityManager) {

        // Authentication gateways
        // /authc/** rule covers /authc , /authc?q=name /authc#anchor urls as well
        configuration.add(factory.createChain(URL_LOGIN).add(factory.anon()).build());
        configuration.add(factory.createChain(URL_SUCCESS).add(factory.user()).build());
        configuration.add(factory.createChain(URL_UNAUTHORIZED).add(factory.user()).build());

        for (String[] linkPathPermission : LINK_PATH_PERMISSIONS) {
            try {
                LOG.info("Permission " + linkPathPermission[0] + " > " + linkPathPermission[1] + " > " + linkPathPermission[2] );
                configuration.add(factory.createChain(linkPathPermission[1]).add(factory.perms(), linkPathPermission[2]).build());
            } catch (Exception e) {}
        }
    }

    private static PermissionsAuthorizationFilter getPermissionFilter(SecurityFilterChainFactory factory) {
        PermissionsAuthorizationFilter permFilter = factory.perms();
        permFilter.setUnauthorizedUrl(URL_UNAUTHORIZED);
        return permFilter;
    }
   /*
    private static final Map<String, String> LDAP_GROUP_SHIRO_ROLE_MAPPING = new LinkedHashMap(){
        {
            put("LDAP_GRP_CUSTOMER", AppModule.ROLE_CUSTOMER);
            put("LDAP_GRP_SELLER", AppModule.ROLE_SELLER);
            put("LDAP_GRP_EDITOR", AppModule.ROLE_EDITOR);
            put("LDAP_GRP_ADMIN", AppModule.ROLE_ADMIN);
        }
    };

    private static final Map<String, String[]> ROLES_PERMISSIONS = new  LinkedHashMap(){
        {
            put(AppModule.ROLE_CUSTOMER, new String[]{AppModule.PERMISSION_CUSTOMER});
            put(AppModule.ROLE_SELLER, new String[]{AppModule.PERMISSION_SELLER});
            put(AppModule.ROLE_EDITOR, new String[]{AppModule.PERMISSION_EDITOR});
            put(AppModule.ROLE_ADMIN, new String[]{AppModule.PERMISSION_CUSTOMER,AppModule.PERMISSION_SELLER,AppModule.PERMISSION_EDITOR,AppModule.PERMISSION_ADMIN});
        }
    };

    public static void contributeWebSecurityManager(Configuration<org.apache.shiro.realm.Realm> configuration,
                                                    @Autobuild LdapRealm ldapRealm) {
        // http://stackoverflow.com/questions/12173492/shiro-jndildaprealm-authorization-agains-ldap
        // http://shiro.apache.org/configuration.html#Configuration-SecurityManagerfromanINIresource

        ldapRealm.processRoleDefinitions(ROLES_PERMISSIONS);
        ldapRealm.setGroupRolesMap(LDAP_GROUP_SHIRO_ROLE_MAPPING);

        ldapRealm.setUserDnTemplate("userd={0}, ");
        JndiLdapContextFactory contextFactory = ((JndiLdapContextFactory) ldapRealm.getContextFactory());

        contextFactory.setUrl("ldap://server");
        contextFactory.setAuthenticationMechanism("simple");
        contextFactory.setSystemUsername("");
        contextFactory.setSystemPassword("");

        ldapRealm.setSearchBase("");
        ldapRealm.setGroupNameAttribute("");
    }
      */

    /**
     * This is a contribution to the RequestHandler service configuration. This is how we extend
     * Tapestry using the HSTSPolicy filter.
     */
    // https://svn.apache.org/repos/asf/tapestry/tapestry5/trunk/quickstart/filtered/archetype-resources/src/main/java/services/AppModule.java
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration)
    {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        configuration.add("HSTSPolicy", new HSTSPolicyHeader()); // HTTPS only: No HTTP allowed
        configuration.add("CSPolicy", new CSPolicyHeader());     // Restrict JS execution: no CDN
        //configuration.add("Timing", filter);
    }
}
