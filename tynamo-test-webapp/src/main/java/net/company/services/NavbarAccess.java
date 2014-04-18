package net.company.services;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.Configuration;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityService;
import org.tynamo.security.services.impl.SecurityFilterChain;

/**
 * Created by based on 18/04/14.
 */
public interface NavbarAccess
{
    public String[][] getPermissions();
    public void setupSecurity(Configuration<SecurityFilterChain> configuration,
                              SecurityFilterChainFactory factory, WebSecurityManager securityManager);
    public java.util.Map<String, String> getPageNames(SecurityService securityService);
}
