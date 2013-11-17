package net.company.components;

import net.company.services.AppModule;
import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Layout component for pages of application webapp.
 */
// http://getbootstrap.com/examples/theme/
@Import(stack="core",
        stylesheet={"context:styles/bootstrap-bootswatch-slate.css",
              //  "context:styles/bootstrap-theme.min.css",
                "context:styles/empty.css"})
public class Layout
{
    @Inject
    private Logger LOG;

    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Persist
    private String uname;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private Request request;

    @Inject
    private ComponentResources resources;

    @Inject
    @Property
    private SecurityService securityService;

    @Inject
    private LoginContextService loginContextService;

    @Persist
    private String[] accessiblePages;

    public String getClassForPageName()
    {
        if (uname==null) {
            uname = (String) SecurityUtils.getSubject().getPrincipal();
        }
        if (pageName==null) {
            pageName = AppModule.URL_SUCCESS.substring(1);
        }
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(resources.getPageName().toLowerCase()
                        + " / " + pageName.toLowerCase());
            }
            return resources.getPageName().toLowerCase().startsWith(pageName.toLowerCase())
                    ? "active"
                    : null;
        } catch (Exception e){
            LOG.error(resources.getPageName(), e);
            return null;
        }
    }

    public String[] getPageNames()
    {
        if (accessiblePages==null) {
            List<String> pages = new ArrayList<String>();
            for (String[] pageOrDir : AppModule.LINK_PATH_PERMISSIONS)
            {
                if (pageOrDir!=null){
                    try {
                        if (pageOrDir.length==1){
                            // process page with no access permission restrictions
                            pages.add(pageOrDir[0]);
                        } else {
                            if (securityService.hasPermission(pageOrDir[2])) {
                                pages.add(pageOrDir[0]);
                            }
                        }
                    } catch (Exception e) {
                        LOG.error("Failed to proceed to register pageOrDir "+ pageOrDir[0]
                         + " for permission " + pageOrDir[2]);
                    }
                }
            }
            accessiblePages = pages.toArray(new String[pages.size()]);
        }
        return accessiblePages;
    }

    @Log
    @OnEvent(value=org.apache.tapestry5.EventConstants.ACTION, component="logout")
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
            LOG.error("Invalidating HTTP session...");
        }

        return loginContextService.getLoginPage();
    }
}
