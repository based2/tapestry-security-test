package net.company.services;

import org.apache.tapestry5.ioc.annotations.EagerLoad;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.ioc.services.PipelineBuilder;
import org.slf4j.Logger;
import org.tynamo.security.SecuritySymbols;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.impl.SecurityFilterChainFactoryImpl;
import org.tynamo.security.shiro.AccessControlFilter;
import org.tynamo.security.shiro.authz.PermissionsAuthorizationFilter;
import org.tynamo.security.shiro.authz.RolesAuthorizationFilter;

/**
 * Fix HTTP 401 Redirection for Shiro Tynamo
 *
 *  configured in AppModule in bind method.
 *  src: http://mail-archives.apache.org/mod_mbox/tapestry-users/201108.mbox/%3C1314385371576-4739125.post@n5.nabble.com%3E
 *  tested with tynamo 0.5.1 http://grepcode.com/file/repo1.maven.org/maven2/org.tynamo/tapestry-security/0.5.0/org/tynamo/security/services/impl/SecurityFilterChainFactoryImpl.java
 *
 * @author cqasker
 * @date 2013/06/28 14:51
 * http://docs.codehaus.org/display/TYNAMO/Source
 *
 * http://jira.codehaus.org/browse/TYNAMO-147
 *
 * http://flowlogix.googlecode.com/hg-history/4c2325d15a7395f72fb6f822284e028a99d71e28/tapestry-services/src/main/java/com/flowlogix/web/services/internal/ExceptionHandlerAssistantImpl.java
 */
@EagerLoad
public class RedirectHTTP401Error extends SecurityFilterChainFactoryImpl
{
   // public SecurityFilterChainFactoryImpl(org.slf4j.Logger logger, org.apache.tapestry5.ioc.services.PipelineBuilder builder, org.tynamo.security.internal.services.LoginContextService loginContextService, @org.apache.tapestry5.ioc.annotations.Inject @org.apache.tapestry5.ioc.annotations.Symbol("security.successurl") java.lang.String successUrl, @org.apache.tapestry5.ioc.annotations.Inject @org.apache.tapestry5.ioc.annotations.Symbol("security.loginurl") java.lang.String loginUrl, @org.apache.tapestry5.ioc.annotations.Inject @org.apache.tapestry5.ioc.annotations.Symbol("security.unauthorizedurl") java.lang.String unauthorizedUrl, @org.apache.tapestry5.ioc.annotations.Inject @org.apache.tapestry5.ioc.annotations.Symbol("security.redirecttosavedurl") boolean redirectToSavedUrl) { /* compiled code */ }

    public RedirectHTTP401Error(Logger logger,
                       @Inject PipelineBuilder builder,
                       @Inject LoginContextService login,
                       @Symbol(SecuritySymbols.SUCCESS_URL) String successUrl,
                       @Inject
                       @Symbol(SecuritySymbols.LOGIN_URL) String loginUrl,
                       @Inject
                       @Symbol(SecuritySymbols.UNAUTHORIZED_URL) String unauthorizedUrl,
                       @Inject
                       @Symbol(SecuritySymbols.REDIRECT_TO_SAVED_URL) boolean redirectToSavedUrl)
    {
        super(logger, builder, login, successUrl, loginUrl, unauthorizedUrl, redirectToSavedUrl);
    }

    @Override
    public RolesAuthorizationFilter roles()
    {
        RolesAuthorizationFilter r = super.roles();
        r.setUnauthorizedUrl(AccessControlFilter.UNAUTHORIZED_URL);
        //the key setter that for some reason wasn't set
        return r;
    }

    @Override
    public PermissionsAuthorizationFilter perms()
    {
        PermissionsAuthorizationFilter p = super.perms();
        p.setUnauthorizedUrl(AccessControlFilter.UNAUTHORIZED_URL);
        //the key setter that for some reason wasn't set
        return p;
    }
}
