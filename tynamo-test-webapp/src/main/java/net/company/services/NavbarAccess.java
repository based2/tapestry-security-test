package net.company.services;

import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.tynamo.security.services.SecurityFilterChainFactory;
import org.tynamo.security.services.SecurityService;
import org.tynamo.security.services.impl.SecurityFilterChain;

import java.util.Map;

/**
 * Created by based on 18/04/14.
 */
public interface NavbarAccess {
    void setupSecurity(OrderedConfiguration<SecurityFilterChain> configuration,
                       SecurityFilterChainFactory factory, WebSecurityManager securityManager);
    Map<String, String> getPageNames(SecurityService securityService);
}
