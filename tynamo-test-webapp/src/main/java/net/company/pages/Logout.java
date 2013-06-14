package net.company.pages;

//import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.ioc.annotations.Inject;
//import org.apache.tapestry5.services.Request;

import org.tynamo.security.services.SecurityService;

/**
 * Created with IntelliJ IDEA.
 * User: chandba
 * Date: 03/06/13
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */
// http://mail-archives.apache.org/mod_mbox/tapestry-users/201103.mbox/%3CAANLkTimVy8dbnzdtvr3eBWt3q2Wer4k_S4TbktK=KiYY@mail.gmail.com%3E
// http://comments.gmane.org/gmane.comp.java.tynamo.user/710
public class Logout {

    //@Inject
    //private Request request;

    @org.apache.tapestry5.ioc.annotations.Inject
    private org.tynamo.security.services.SecurityService securityService;

    Object onActivate() {
        /***
         * The order is important as Shiro uses the 'real' httpsession.*
         * Upon logout shiro clears session and cache and if session is already
         * invalidated - an exception occur.
         *
         * Tapestry on the other hand uses a session facade and if the 'real'
         * httpsession is invalidated then the facade don't know about it as it
         * maintains it's own state.
         * This causes tapestry to fail internally.
         *
         * So the strategy is: Let shiro perform logout, then invalidate tapestry's
         * session and catch IllegalStateException as result of it.*
         **/
    /*     // 1. Logout Shiro
        securityService.getSubject().logout();

        //2. Logout legacy
        if(!Validator.isBlankOrNull(cookies.readCookieValue(CookieHelper.LOGIN_COOKIE)))
        {
           cookies.removeCookieValue(CookieHelper.LOGIN_COOKIE);
        }
        try {
          Session session = request.getSession(false);
          if(session!=null && !session.isInvalidated())
            session.invalidate();
        } catch (IllegalStateException e) {
            // could happen here if session was already invalidated outside of
            tapestry
           }
           return Index.class;
        }*/

        /*
        SecurityUtils.getSubject().logout();
        try {
            // the session is already invalidated, but need to cause an exception since tapestry doesn't know about it
            // and you'll get a container exception message instead without this. Unfortunately, there's no way of
            // configuring Shiro to not invalidate sessions right now. See DefaultSecurityManager.logout()
            // There's a similar issues in Tapestry - Howard has fixed, but no in T5.2.x releases yet
            request.getSession(false).invalidate();
        } catch (Exception e) { }   */

        securityService.getSubject().logout();
        return this;
    }
}
