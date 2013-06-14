package net.company.services;

import org.apache.shiro.web.mgt.*;
import org.apache.tapestry5.*;
import org.apache.tapestry5.ioc.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.services.*;
import org.tynamo.security.*;
import org.tynamo.security.services.*;
import org.tynamo.security.services.impl.*;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
@org.apache.tapestry5.ioc.annotations.SubModule({org.tynamo.security.services.SecurityModule.class})
public class AppModule
{
    private final static org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(net.company.services.AppModule.class);

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

	  
    public static void contributeApplicationDefaults(
            org.apache.tapestry5.ioc.MappedConfiguration<String, String> configuration)
    {
        // Contributions to ApplicationDefaults will override any contributions to
        // FactoryDefaults (with the same key). Here we're restricting the supported
        // locales to just "en" (English). As you add localised message catalogs and other assets,
        // you can extend this list of locales (it's a comma separated series of locale names;
        // the first locale name is the default when there's no reasonable match).
        
        configuration.add(org.apache.tapestry5.SymbolConstants.SUPPORTED_LOCALES, "en");

        // The factory default is true but during the early stages of an application
        // overriding to false is a good idea. In addition, this is often overridden
        // on the command line as -Dtapestry.production-mode=false
        configuration.add(org.apache.tapestry5.SymbolConstants.PRODUCTION_MODE, "false");

        // The application version number is incorprated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions.
        configuration.add(org.apache.tapestry5.SymbolConstants.APPLICATION_VERSION, "1.0");

        configuration.add(org.apache.tapestry5.SymbolConstants.START_PAGE_NAME, "SEC");
        
        configuration.add(org.apache.tapestry5.SymbolConstants.SECURE_ENABLED, "true");

        configuration.add(org.apache.tapestry5.SymbolConstants.HOSTPORT, "8080");
        configuration.add(org.apache.tapestry5.SymbolConstants.HOSTPORT_SECURE, "8443");
        
		// Tynamo's tapestry-security module configuration
		configuration.add(org.tynamo.security.SecuritySymbols.LOGIN_URL, LOYALTY_LOGIN_URL);
		configuration.add(org.tynamo.security.SecuritySymbols.SUCCESS_URL, LOYALTY_SUCCESS_URL);
		configuration.add(org.tynamo.security.SecuritySymbols.UNAUTHORIZED_URL, LOYALTY_UNAUTHORIZED_URL);
    }

	public void contributeMetaDataLocator(org.apache.tapestry5.ioc.MappedConfiguration<String,String> configuration)
	{
		// Enabling @Secure only on some pages (the remaining ones are insecure)
	    configuration.add(org.apache.tapestry5.MetaDataConstants.SECURE_PAGE, "true");
	}

    // http://tapestry.apache.org/https.html - for dev
    // https://issues.apache.org/jira/browse/TAP5-1973
	/*public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration)
    {
        BaseURLSource source = new BaseURLSource()
        {
            public String getBaseURL(boolean secure)
            {
                String protocol = secure ? "https" : "http";
                String host = "localhost";
                try {
                	host = InetAddress.getLocalHost().getCanonicalHostName();
                } catch ( UnknownHostException e) {
                	e.printStackTrace();
                }
                
                int port = secure ? 8443 : 8080;//9080;

                return String.format("%s://%s:%d", protocol, host, port);
            }
        };

        configuration.add(BaseURLSource.class, source);
    }  */

   /*
    public static void contributeWebSecurityManager(Configuration<org.apache.shiro.realm.Realm> configuration) {
        //ExtendedPropertiesRealm realm = new ExtendedPropertiesRealm("classpath:shiro-users.properties");

        // http://shiro-user.582556.n2.nabble.com/How-to-add-a-role-to-the-subject-td5562700.html
        // http://svn.apache.org/repos/asf/shiro/trunk/core/src/main/java/org/apache/shiro/realm/text/TextConfigurationRealm.java
        // http://shiro.apache.org/static/1.2.1/apidocs/org/apache/shiro/realm/text/TextConfigurationRealm.html#setRoleDefinitions%28java.lang.String%29

        org.apache.shiro.realm.text.TextConfigurationRealm realm = new org.apache.shiro.realm.text.TextConfigurationRealm();

        realm.addRole(ROLE_JUNIOR);
        realm.addRole(ROLE_SENIOR);
        realm.addRole(ROLE_ADMIN);

        realm.setRoleDefinitions(ROLE_DEF_JUNIOR + "\n"
                + ROLE_DEF_SENIOR + "\n"
                + ROLE_DEF_ADMIN);

        realm.setUserDefinitions("junior1" + " = " + "password1," + ROLE_JUNIOR + "\n"
                                + "senior1" + " = " + "password1," + ROLE_SENIOR + "\n"
                                + "admin1" + " = " + "password1," + ROLE_ADMIN);

        realm.init();
        LOG.debug("# RoleDefinitions:\n" + realm.getRoleDefinitions());
        LOG.debug("# UserDefinitions:\n" + realm.getUserDefinitions());  // Warning: print passwords!

        // on page add by example: @RequiresPermissions(PERMISSION_JUNIOR)
        configuration.add(realm);
    }     */

    public static void contributeWebSecurityManager(org.apache.tapestry5.ioc.Configuration<org.apache.shiro.realm.Realm> configuration) {
        configuration.add(new org.tynamo.shiro.extension.realm.text.ExtendedPropertiesRealm("classpath:shiro-users.properties"));
    }

    @org.apache.tapestry5.ioc.annotations.Contribute(org.apache.tapestry5.services.HttpServletRequestFilter.class)
    @org.apache.tapestry5.ioc.annotations.Marker(org.tynamo.security.Security.class)
    public static void setupSecurity(org.apache.tapestry5.ioc.Configuration<org.tynamo.security.services.impl.SecurityFilterChain> configuration,
                                     org.tynamo.security.services.SecurityFilterChainFactory factory, org.apache.shiro.web.mgt.WebSecurityManager securityManager) {
        // Authentication gateways
        // /authc/** rule covers /authc , /authc?q=name /authc#anchor urls as well
        configuration.add(factory.createChain(LOYALTY_LOGIN_URL).add(factory.anon()).build());
        configuration.add(factory.createChain(LOYALTY_SUCCESS_URL).add(factory.user()).build());
        configuration.add(factory.createChain(LOYALTY_UNAUTHORIZED_URL).add(factory.user()).build());


        for (String url : URLS) {

            configuration.add(factory.createChain(url).add(factory.user()).build());

        }
    }


	private static org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter getPermissionFilter(org.tynamo.security.services.SecurityFilterChainFactory factory) {
		org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter permFilter = factory.perms();
		permFilter.setUnauthorizedUrl(LOYALTY_UNAUTHORIZED_URL);
		return permFilter;
	}
}
