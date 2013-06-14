package net.company.pages;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.tapestry5.EventConstants;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

import java.util.Date;

/**
 * Start page of application webapp.
 */

@org.apache.tapestry5.annotations.Secure
public class Index
{
    @org.apache.tapestry5.ioc.annotations.Inject
	@org.apache.tapestry5.annotations.Property
	private org.tynamo.security.services.SecurityService securityService;
	
	@org.apache.tapestry5.annotations.Property
	private String username;
	
	@org.apache.tapestry5.annotations.Property
	private String password;
	
	@org.apache.tapestry5.annotations.Property
	private boolean rememberMe;	

	
	@org.apache.tapestry5.annotations.Component(id="loginForm")
	private org.apache.tapestry5.corelib.components.Form loginForm;

	@org.apache.tapestry5.ioc.annotations.Inject
	private org.tynamo.security.internal.services.LoginContextService loginContextService;
	
	
	@org.apache.tapestry5.annotations.Log
	String onActivate() {
		
		org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		if(subject.isAuthenticated() || subject.isRemembered()) {
			return loginContextService.getSuccessPage();
		} 
		return null;
	}
	    
		
	void setupRender() {
			System.out.println("Index setup render...");
	}	
	
 
	@org.apache.tapestry5.annotations.Log
	@org.apache.tapestry5.annotations.OnEvent(value= org.apache.tapestry5.EventConstants.VALIDATE, component="loginForm")
	public void validation(){
		if(!loginForm.isValid()) {
			return;
		}
	   try{			  
			org.apache.shiro.subject.Subject subject = org.apache.shiro.SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {
				org.apache.shiro.authc.UsernamePasswordToken token = new org.apache.shiro.authc.UsernamePasswordToken(username, password);
				token.setRememberMe(rememberMe);
					
				subject.login(token);
				System.out.println("User [" + subject.getPrincipal() + "] logged in successfully.");
			}
	   }
	   catch(Exception e){
		   loginForm.recordError("Error "+e.getMessage());
	   }
  	}

	
	@org.apache.tapestry5.annotations.Log
	@org.apache.tapestry5.annotations.OnEvent(value= org.apache.tapestry5.EventConstants.SUCCESS, component="loginForm")
	public Object loggingSuccess(){				
			return loginContextService.getSuccessPage();
	}
	
	
	public java.util.Date getCurrentTime()
	{ 
		return new java.util.Date();
	}
}
