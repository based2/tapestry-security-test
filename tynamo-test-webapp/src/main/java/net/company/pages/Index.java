package net.company.pages;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

import java.util.Date;

/**
 * Start page of application
 * http://jumpstart.doublenegative.com.au/jumpstart/examples/input/forms1
 */

@Secure
public class Index
{
    @Inject
    private Logger LOG;

    @Inject
	@Property
	private SecurityService securityService;
	
	@Property
	private String username, password;

    //@Property
    //private String username2;

	//@Property
	//private boolean rememberMe;
	
	@Component(id="loginForm")
	private Form loginForm;

	@Inject
	private LoginContextService loginContextService;

	@Log
	String onActivate() {
		Subject subject = SecurityUtils.getSubject();
		if (subject.isAuthenticated() || subject.isRemembered()) {
			return loginContextService.getSuccessPage();
		} 
		return null;
	}
	
	@Log
	@OnEvent(value= EventConstants.VALIDATE, component="loginForm")
	public void validation() {
		if(!loginForm.isValid()) {
			return;
		}
	   try{			  
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				//token.setRememberMe(rememberMe);
					
				subject.login(token);
				LOG.debug("User [" + subject.getPrincipal() + "] logged in successfully.");
			} else {
                LOG.debug("User [" + subject.getPrincipal() + "] failed to log.");
            }
	   } catch(Exception e) {
		   loginForm.recordError("Error " + e.getMessage());
	   }
  	}

	@Log
	@OnEvent(value= EventConstants.SUCCESS, component="loginForm")
	public Object loggingSuccess() {
		return loginContextService.getSuccessPage();
	}

	public Date getCurrentTime()
	{ 
		return new Date();
	}
}
