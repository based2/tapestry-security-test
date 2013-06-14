package net.company.pages;

public class Success {

    @org.apache.tapestry5.ioc.annotations.Inject
    private org.slf4j.Logger logger;

	@org.apache.tapestry5.ioc.annotations.Inject
	private org.tynamo.security.internal.services.LoginContextService loginContextService;
	
    @org.apache.tapestry5.ioc.annotations.Inject
    private org.apache.tapestry5.services.Request request;

    public String getUserLogin() {
        logger.debug("Authenticated: "+ org.apache.shiro.SecurityUtils.getSubject().isAuthenticated() +
    			" , name: "	+ org.apache.shiro.SecurityUtils.getSubject().getPrincipal());
		return (String) org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
	}

	@org.apache.tapestry5.annotations.Log
	@org.apache.tapestry5.annotations.OnEvent(value=org.apache.tapestry5.EventConstants.ACTION, component="logout")
	public String onActionFromLogout(){
        // Need to call this explicitly to invoke onlogout handlers (for remember me etc.)
        org.apache.shiro.SecurityUtils.getSubject().logout();
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
