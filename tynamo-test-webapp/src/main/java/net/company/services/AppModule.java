package net.company.services;

import net.company.services.http.CSPolicyHeader;
import net.company.services.http.HSTSPolicyHeader;
import net.company.services.http.RedirectHTTP401Error;
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
import org.apache.tapestry5.services.Core;
import org.apache.tapestry5.services.HttpServletRequestFilter;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.javascript.JavaScriptStack;
import org.apache.tapestry5.services.javascript.StackExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.Security;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityModule;
import org.tynamo.security.services.impl.SecurityFilterChain;
import org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter;
import org.tynamo.shiro.extension.realm.text.ExtendedPropertiesRealm;

import javax.crypto.Cipher;
import java.security.NoSuchAlgorithmException;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@SubModule({SecurityModule.class})
public class AppModule
{
    private final static Logger LOG = LoggerFactory.getLogger(AppModule.class);

    public static boolean isProduction = false;

    public static final String URL_LOGIN = "/index";
    public static final String URL_SUCCESS = "/board/Index";//"/success";
    public static final String URL_UNAUTHORIZED = "/AccessDenied";

    public static final String PERMISSION_CUSTOMER = "customerPermission:1";
    public static final String PERMISSION_SELLER = "sellerPermission:1";
    public static final String PERMISSION_EDITOR = "editorPermission:1";
    public static final String PERMISSION_ADMIN = "adminPermission:1";

    public static void contributeApplicationDefaults(MappedConfiguration<String, String> configuration) {
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
            //LINK_PATH_PERMISSIONS[0][0] = null;
            configuration.add(SymbolConstants.PRODUCTION_MODE, "true");
            configuration.add(SymbolConstants.COMPONENT_RENDER_TRACING_ENABLED, "false");
            configuration.add(SymbolConstants.COMPACT_JSON, "true");
            configuration.add(SymbolConstants.COMPRESS_WHITESPACE, "true");
            configuration.add(SymbolConstants.MINIFICATION_ENABLED, "true");
        }
        // todo check http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/HMAC-Passphrase-Could-Be-Much-More-Useful-Correct-Me-If-I-m-Wrong-td5724606.html
        configuration.add(SymbolConstants.HMAC_PASSPHRASE, RandomStringUtils.randomAscii(10));

        configuration.add(SymbolConstants.JAVASCRIPT_INFRASTRUCTURE_PROVIDER, "jquery");
        configuration.add(SymbolConstants.SESSION_LOCKING_ENABLED, "true");
        configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en,fr");
        //configuration.add(SymbolConstants.DEFAULT_STYLESHEET, "context:styles/empty.css");


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

        // Check JCE Unlimited StrengthJurisdictionPolicyFilesInstalled
        try {
            if (Cipher.getMaxAllowedKeyLength("AES")<2147483647){
                LOG.error("### JCE Unlimited Strength Jurisdiction Policy Files is NOT Installed.\n" +
                        "http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html http://www.oracle.com/technetwork/java/javase/downloads/jce-7-download-432124.html");
            }
        } catch (NoSuchAlgorithmException e) {
            LOG.error("### JCA JSSE JCE API not found. - http://www.oracle.com/technetwork/java/javase/tech/index-jsp-136007.html");
        }
    }

    // http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/HMAC-Passphrase-Could-Be-Much-More-Useful-Correct-Me-If-I-m-Wrong-td5724606.html#a5724608
    // http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/Advising-and-Decoration-order-td5723770.html
    // http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/Decorating-IOC-Services-td2467899.html
    // http://code.google.com/p/gsoc2011-csrf-protection/source/browse/trunk/csrfprotection/src/main/java/org/apache/tapestry5/csrfprotection/services/CsrfProtectionModule.java
    // http://apache-tapestry-mailing-list-archives.1045711.n5.nabble.com/overriding-a-service-implementation-td5713709.html
    // Redirect user to a new login in case of HMAC incompatibility (ucase: new deployment with HMAC value change)
   /* public ClientDataEncoder decorateClientDataEncoder(final ClientDataEncoder delegate)

    {
        return new ClientDataEncoder() {

            @Inject
            private Logger logger;

            @Inject
            private LoginContextService loginContextService;

            private final static String HMAC_DOES_NOT_MATCH = "Client data associated with the current request appears to have been tampered with " +
                    "(the HMAC signature does not match).";

            @Override
            public ClientDataSink createSink() {
                return delegate.createSink();
            }

            @Override
            public ObjectInputStream decodeClientData(String s) throws IOException {
                try {
                    return delegate.decodeClientData(s);
                } catch (IOException ex) {
                    if (ex.getMessage().equals(HMAC_DOES_NOT_MATCH)) {
                        logger.warn("HMAC does not match for user: " + SecurityUtils.getSubject().getPrincipal() +", redirecting to login page.");
                        //TapestryRealmSecurityManager
                        //saveRequestAndRedirectToLogin(request, response);
                        loginContextService.redirectToSavedRequest(loginContextService.getLoginURL());
                    } else {
                        throw ex;
                    }
                }
                return null;
            }

            @Override
            public ObjectInputStream decodeEncodedClientData(String s) throws IOException {
                return delegate.decodeEncodedClientData(s);
            }
        };
    } */

    public static void bind(final ServiceBinder binder) {
        binder.bind(SecurityFilterChainFactory.class, RedirectHTTP401Error.class).withId("RedirectHTTP401Error");
        binder.bind(NavbarAccess.class, NavbarAccessImpl.class);
        binder.bind(Audit.class, net.company.services.AuditImpl.class);
        //binder.bind(Customer.class, CustomerImpl.class);
    }

    // http://tapestry.apache.org/security-faq.html
   /* @Contribute(ClientWhitelist.class)
    public static void turnOffLocalhostInProduction(OrderedConfiguration<WhitelistAnalyzer> configuration,
                                                    @Symbol(SymbolConstants.PRODUCTION_MODE) boolean productionMode) {
        if (productionMode) { configuration.override("LocalhostOnly", null); }
    }*/

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Security - Tynamo/Shiro
    // http://tapestry.apache.org/https.html#HTTPS-SecuringMultiplePages

    public void contributeMetaDataLocator(MappedConfiguration<String, String> configuration) {
        // Enabling @Secure only on some pages (the remaining ones are insecure)
        configuration.add(MetaDataConstants.SECURE_PAGE, "true");
    }

    public static void contributeWebSecurityManager(Configuration<Realm> configuration) {
        configuration.add(new ExtendedPropertiesRealm("classpath:shiro-users.properties"));
    }

    @Contribute(ServiceOverride.class)
    public static void overrideSecurityFilterChainFactory(MappedConfiguration<Class<?>, Object> configuration,
                                                          @Local SecurityFilterChainFactory securityFilterChainFactory) {
        configuration.add(SecurityFilterChainFactory.class, securityFilterChainFactory);
    }

    private static boolean IS_SECURITY_ENABLED = false;

    @Contribute(HttpServletRequestFilter.class)
    @Security
    //@Marker(Security.class)
    public static void setupSecurity(OrderedConfiguration<SecurityFilterChain> configuration,
                                     SecurityFilterChainFactory factory,
                                     WebSecurityManager securityManager, NavbarAccess navbarAccess) {
        configuration.add("ModulesCompressed",
                factory.createChain("/modules.gz/**").add(factory.anon()).build());
        configuration.add("Modules",
                factory.createChain("/modules/**").add(factory.anon()).build());
        configuration.add("Assets",
                factory.createChain("/assets/**").add(factory.anon()).build());
        configuration.add("Favicon",
                factory.createChain("/favicon.ico").add(factory.anon()).build());
        configuration.add("index",
                factory.createChain("/index.loginform").add(factory.anon()).build());

        if (!IS_SECURITY_ENABLED) {
            navbarAccess.setupSecurity(configuration, factory, securityManager);
            IS_SECURITY_ENABLED = true;
        }
    }

    @Contribute(JavaScriptStack.class)
    @Core
    public static void setupCoreJavaScriptStack(OrderedConfiguration<StackExtension> configuration) {
        configuration.override("exception-frame.css", null);
        configuration.override("tapestry.css", null);
        configuration.override("tapestry-console.css", null);
        configuration.override("tree.css", null);
    }

    private static PermissionsAuthorizationFilter getPermissionFilter(SecurityFilterChainFactory factory) {
        final PermissionsAuthorizationFilter permFilter = factory.perms();
        permFilter.setUnauthorizedUrl(URL_UNAUTHORIZED);
        return permFilter;
    }
   /*
    private static final Map<String, String> LDAP_GROUP_SHIRO_ROLE_MAPPING = new LinkedHashMap(){
        {
            put("LDAP_GRP_CUSTOMER", AppModule.ROLE_CUSTOMER);
            put("LDAP_GRP_SELLER", AppModule.ROLE_SELLER);
            put("LDAP_GRP_EDITOR", AppModule.ROLE_EDITOR);
            //put("LDAP_GRP_ADMIN", AppModule.ROLE_ADMIN);
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

    private static final Map<String, String> USERS_ROLE_MAPPING = new  LinkedHashMap(){
        {
            put("admin1", AppModule.ROLE_ADMIN);
        }
    };

    public static void contributeWebSecurityManager(Configuration<org.apache.shiro.realm.Realm> configuration,
                                                    @Autobuild LdapRealm ldapRealm) {
        // http://stackoverflow.com/questions/12173492/shiro-jndildaprealm-authorization-agains-ldap
        // http://shiro.apache.org/configuration.html#Configuration-SecurityManagerfromanINIresource

        ldapRealm.processRoleDefinitions(ROLES_PERMISSIONS);
        ldapRealm.setGroupRolesMap(LDAP_GROUP_SHIRO_ROLE_MAPPING);
        ldapRealm.setUserRolesMapFilter(USERS_ROLE_MAPPING);

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
    public void contributeRequestHandler(OrderedConfiguration<RequestFilter> configuration) {
        // Each contribution to an ordered configuration has a name, When necessary, you may
        // set constraints to precisely control the invocation order of the contributed filter
        // within the pipeline.

        configuration.add("HSTSPolicy", new HSTSPolicyHeader()); // HTTPS only: No HTTP allowed
        configuration.add("CSPolicy", new CSPolicyHeader());     // Restrict JS execution: no CDN
        //configuration.add("AutoLogout", new AutoLogoutHeader());
        //configuration.add("Timing", filter);
    }
}
