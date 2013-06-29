package net.company.services;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.MetaDataConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Marker;
import org.apache.tapestry5.ioc.annotations.SubModule;
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

    public static final String LOYALTY_LOGIN_URL = "/index";
    public static final String LOYALTY_SUCCESS_URL = "/success";
    public static final String LOYALTY_UNAUTHORIZED_URL = "/AccessDenied";

    private final static String[] URLS = {"/junior/**", "/senior/**", "/admin/**"};

    public static final String ROLE_JUNIOR = "juniorRole";
    public static final String ROLE_SENIOR = "seniorRole";
    public static final String ROLE_ADMIN = "adminRole";

    public static final String PERMISSION_JUNIOR = "juniorPermission:1";
    public static final String PERMISSION_SENIOR = "seniorPermission:1";
    public static final String PERMISSION_ADMIN = "adminPermission:1";

    public static final String ROLE_DEF_JUNIOR = ROLE_JUNIOR + " = " + PERMISSION_JUNIOR;
    public static final String ROLE_DEF_SENIOR = ROLE_SENIOR + " = " + PERMISSION_JUNIOR + "," + PERMISSION_SENIOR;
    public static final String ROLE_DEF_ADMIN = ROLE_ADMIN + " = " + PERMISSION_JUNIOR + "," + PERMISSION_SENIOR + "," + PERMISSION_ADMIN;


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
        configuration.add(SecuritySymbols.LOGIN_URL, LOYALTY_LOGIN_URL);
        configuration.add(SecuritySymbols.SUCCESS_URL, LOYALTY_SUCCESS_URL);
        configuration.add(SecuritySymbols.UNAUTHORIZED_URL, LOYALTY_UNAUTHORIZED_URL);
    }

    public void contributeMetaDataLocator(MappedConfiguration<String,String> configuration)
    {
        // Enabling @Secure only on some pages (the remaining ones are insecure)
        configuration.add(MetaDataConstants.SECURE_PAGE, "true");
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
        configuration.add(new ExtendedPropertiesRealm("classpath:shiro-users.properties"));
    }

    @Contribute(HttpServletRequestFilter.class)
    @Marker(Security.class)
    public static void setupSecurity(Configuration<SecurityFilterChain> configuration,
                                     SecurityFilterChainFactory factory,
                                     WebSecurityManager securityManager) {
        // Authentication gateways
        // /authc/** rule covers /authc , /authc?q=name /authc#anchor urls as well
        configuration.add(factory.createChain(LOYALTY_LOGIN_URL).add(factory.anon()).build());
        configuration.add(factory.createChain(LOYALTY_SUCCESS_URL).add(factory.user()).build());
        configuration.add(factory.createChain(LOYALTY_UNAUTHORIZED_URL).add(factory.user()).build());

        for (String url : URLS) {
            configuration.add(factory.createChain(url).add(factory.user()).build());
        }
    }

    private static PermissionsAuthorizationFilter getPermissionFilter(SecurityFilterChainFactory factory) {
        PermissionsAuthorizationFilter permFilter = factory.perms();
        permFilter.setUnauthorizedUrl(LOYALTY_UNAUTHORIZED_URL);
        return permFilter;
    }
}
