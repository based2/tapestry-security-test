package net.company.pages;

import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;

public class Success {

    @Inject
    private Logger logger;

	@Inject
	private LoginContextService loginContextService;
	
    @Inject
    private Request request;

    public String getUserLogin() {
        logger.debug("Authenticated: "+ SecurityUtils.getSubject().isAuthenticated() +
    			" , name: "	+ SecurityUtils.getSubject().getPrincipal());
		return (String) SecurityUtils.getSubject().getPrincipal();
	}

	@Log
	@OnEvent(value= EventConstants.ACTION, component="logout")
	public String onActionFromLogout(){
        // Need to call this explicitly to invoke onlogout handlers (for remember me etc.)
        SecurityUtils.getSubject().logout();
        try {
            // the session is already invalidated, but need to cause an exception since tapestry doesn't know about it
            // and you'll get a container exception message instead without this. Unfortunately, there's no way of
            // configuring Shiro to not invalidate sessions right now. See DefaultSecurityManager.logout()
            // There's a similar issues in Tapestry - Howard has fixed, but no in T5.2.x releases yet
            request.getSession(false).invalidate();
        } catch (Exception e) {
        	logger.error("Invalidating HTTP session...");
        }

        return loginContextService.getLoginPage();		
	}
}
