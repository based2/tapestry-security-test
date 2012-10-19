package net.company.pages;

import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Log;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Secure;
import org.apache.tapestry5.beaneditor.Validate;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

/**
 * Start page of application webapp.
 */

@Secure
public class Index
{

	@Inject
	@Property
	private SecurityService securityService;	
	
	@Property
	private String username;
	
	@Property
	private String password;
	
	@Property
	private boolean rememberMe;	

	
	@Component(id="loginForm")	
	private Form loginForm;

	@Inject
	private LoginContextService loginContextService;
	
	
	@Log
	String onActivate() {
		
		Subject subject = SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered()) {
			return loginContextService.getSuccessPage();
		} 
		return null;
	}
	    
		
	void setupRender() {
			System.out.println("Index setup render...");
	}	
	
 
	@Log
	@OnEvent(value=EventConstants.VALIDATE, component="loginForm")
	public void validation(){
		if(!loginForm.isValid()) {
			return;
		}
	   try{			  
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				token.setRememberMe(rememberMe);
					
				subject.login(token);
				System.out.println("User [" + subject.getPrincipal() + "] logged in successfully.");
			}
	   }
	   catch(Exception e){
		   loginForm.recordError("Error "+e.getMessage());
	   }
  	}

	
	@Log
	@OnEvent(value=EventConstants.SUCCESS, component="loginForm")		
	public Object loggingSuccess(){				
			return loginContextService.getSuccessPage();
	}
	
	
	public Date getCurrentTime() 
	{ 
		return new Date(); 
	}
}
