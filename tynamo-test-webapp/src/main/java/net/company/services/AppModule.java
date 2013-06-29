package net.company.services;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.MetaDataConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.SubModule;
import org.apache.tapestry5.ioc.services.ServiceOverride;
import org.apache.tapestry5.services.HttpServletRequestFilter;
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

    public static String[][] LINK_PATH_PERMISSIONS = new String[][]{
            {"Board","/board/**",PERMISSION_CUSTOMER},
            {"Stats","/stats/**",PERMISSION_SELLER},
            {"Inventory","/inventory/**",PERMISSION_EDITOR},
            {"Controls","/controls/**",PERMISSION_EDITOR},
            {"Admin","/admin/**",PERMISSION_ADMIN},
            {"About"},
            {"Contact"}
    };

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).

        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(SymbolConstants.PRODUCTION_MODE, "false");

        configuration.add(SymbolConstants.COMPONENT_RENDER_TRACING_ENABLED, "false");
        configuration.add(SymbolConstants.COMPACT_JSON, "false");
        configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "false");
        configuration.add(SymbolConstants.MINIFICATION_ENABLED, "false");

        //configuration.add(SymbolConstants.DEFAULT_STYLESHEET, "context:styles/empty.css");

        configuration.add(SymbolConstants.HMAC_PASSPHRASE, "fg@Cv09h-°)&vRF 78h_9:");

        // The application version number is incorprated into URLs for some
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
        // configuration.add(factory.createChain(url).add(factory.user()).build());
        /*configuration.add(factory.createChain("/admin/**").add(factory.perms(), "architectPermission:1").build());
        configuration.add(factory.createChain("/board/**").add(factory.perms(), "ccorPermission:1").build());
        configuration.add(factory.createChain("/controls/**").add(factory.perms(), "operationalPermission:1").build());
        configuration.add(factory.createChain("/inventory/**").add(factory.perms(), "operationalPermission:1").build());
        configuration.add(factory.createChain("/stats/**").add(factory.perms(), "officialPermission:1").build());*/
    }

    private static PermissionsAuthorizationFilter getPermissionFilter(SecurityFilterChainFactory factory) {
        PermissionsAuthorizationFilter permFilter = factory.perms();
        permFilter.setUnauthorizedUrl(URL_UNAUTHORIZED);
        return permFilter;
    }
}
