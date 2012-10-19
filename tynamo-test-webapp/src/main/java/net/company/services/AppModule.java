package net.company.services;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.MetaDataConstants;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.BaseURLSource;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.impl.SecurityFilterChain;
import org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter;

/**
 * This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
 * configure and extend Tapestry, or to place your own service definitions.
 */
public class AppModule
{
	
	public static final String LOYALTY_LOGIN_URL = "/index";
	public static final String LOYALTY_SUCCESS_URL = "/portada";
	public static final String LOYALTY_UNAUTHORIZED_URL = "/AccessDenied";

	  
    public static void contributeApplicationDefaults(
            MappedConfiguration<String, String> configuration)
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

        // The application version number is incorprated into URLs for some
        // assets. Web browsers will cache assets because of the far future expires
        // header. If existing assets are changed, the version number should also
        // change, to force the browser to download new versions.
        configuration.add(SymbolConstants.APPLICATION_VERSION, "1.0");
        
        
        configuration.add(SymbolConstants.SECURE_ENABLED, "true");
        
        
		// Tynamo's tapestry-security module configuration
		configuration.add(SecuritySymbols.LOGIN_URL, LOYALTY_LOGIN_URL);
		configuration.add(SecuritySymbols.SUCCESS_URL, LOYALTY_SUCCESS_URL);
		configuration.add(SecuritySymbols.UNAUTHORIZED_URL, LOYALTY_UNAUTHORIZED_URL);        
    }

	public void contributeMetaDataLocator(MappedConfiguration<String,String> configuration)
	{
		// Enabling @Secure only on some pages (the remaining ones are insecure)
	    //configuration.add(MetaDataConstants.SECURE_PAGE, "true");
	}


	public static void contributeServiceOverride(MappedConfiguration<Class,Object> configuration)
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
                
                int port = secure ? 8443 : 9080;

                return String.format("%s://%s:%d", protocol, host, port);
            }
        };

        configuration.add(BaseURLSource.class, source);
    }
	
	
	@Contribute(WebSecurityManager.class)
	public static void addRealms(Configuration<Realm> configuration) {
		//ExtendedPropertiesRealm realm = new ExtendedPropertiesRealm("classpath:shiro-users.properties");
		
		TextConfigurationRealm realm = new TextConfigurationRealm();
		
		realm.addRole("juniorRole");
		realm.addRole("seniorRole");
		realm.addRole("adminRole");
		
		realm.addAccount("junior1", "password1", "juniorRole");
		realm.addAccount("senior1", "password1", "seniorRole");
		realm.addAccount("admin1", "password1", "adminRole");
		
		configuration.add(realm);
	}	
	
	
	private static PermissionsAuthorizationFilter getPermissionFilter(SecurityFilterChainFactory factory) {
		PermissionsAuthorizationFilter permFilter = factory.perms();
		permFilter.setUnauthorizedUrl(LOYALTY_UNAUTHORIZED_URL);
		return permFilter;
	} 
	
	// Starting from 0.4.6, you can also use a marker annotation:
	// @Contribute(HttpServletRequestFilter.class) @Security public static void securePaths(...)
	public static void contributeSecurityConfiguration(Configuration<SecurityFilterChain> configuration,
	            SecurityFilterChainFactory factory) {
		
	    // /authc/** rule covers /authc , /authc?q=name /authc#anchor urls as well
		configuration.add(factory.createChain("/index").add(factory.anon()).build());
		configuration.add(factory.createChain("/portada").add(factory.user()).build());
	}
		
    
  
}
