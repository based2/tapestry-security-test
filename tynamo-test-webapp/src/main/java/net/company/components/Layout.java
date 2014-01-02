package net.company.components;

import net.company.services.AppModule;
import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Layout component for generic application's pages
 * <p/>
 * todo update layout template, bootswatch.css, and icon config in AppModule and fix it
 */
// http://getbootstrap.com/examples/theme/
@Import(stack = "core",
        stylesheet = {"context:styles/bootstrap-bootswatch-slate.css",
                //  "context:styles/bootstrap-theme.min.css",
                "context:font-awesome-4.0.3/css/font-awesome.min.css",
                "context:styles/empty.css"})
public class Layout
{
    @Inject
    private Logger LOG;

    /**
     * Page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

    @Property
    @Persist
    private List<String> pageNames;

    @Property
    private String icon;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private String sidebarTitle;

    @Property
    @Parameter(defaultPrefix = BindingConstants.LITERAL)
    private Block sidebar;

    @Inject
    private ComponentResources resources;

    @Inject
    @Property
    private SecurityService securityService;

    @Property
    @Persist
    //@ImmutableSessionPersistedObject http://tapestry.apache.org/persistent-page-data.html#PersistentPageData-ClusteringIssues
    private String uname;

    @Inject
    private Request request;

    @Inject
    private LoginContextService loginContextService;

    @Persist
    private Map<String, String> pageIcons;

    @SetupRender
    public void init()
    {
        this.loadPageNames();
    }

    public String loadIcon()
    {
        if (pageIcons == null) {
            loadPageNames();
        }
        return pageIcons.get(pageName);
    }

    public boolean isIcon()
    {
        if (pageIcons == null) {
            loadPageNames();
        }
        for (String pageN : pageIcons.keySet()) {
            if (pageName.equals(pageN)) {
                if (!InternalUtils.isBlank(pageIcons.get(pageN))) {
                    icon = pageIcons.get(pageN);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * Init current navbar with its active state
     * <p/>
     * <pre>
     *    <t:loop class="prop:classForPageName()">
     * </pre>
     *
     * @return active | null
     */
    // todo cache it if page does not change
    public String getClassForPageName()
    {
        if (InternalUtils.isBlank(uname)) {
            uname = (String) SecurityUtils.getSubject().getPrincipal();
        }
        if (InternalUtils.isBlank(pageName)) {
            pageName = AppModule.URL_SUCCESS.substring(1);
            this.loadPageNames();
        }

        if (InternalUtils.isBlank(icon)) {
            icon = this.loadIcon();
        }
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(resources.getPageName().toLowerCase() + " / " + pageName.toLowerCase());
            }
            return resources.getPageName().toLowerCase().startsWith(pageName.toLowerCase())
                    ? "active"
                    : null;

        } catch (Exception e) {
            LOG.error(resources.getPageName(), e);
            return null;
        }
    }

    private void load(String pageName, String iconName)
    {
        if (iconName==null) iconName = "";
        this.pageIcons.put(pageName, iconName);
    }

    private void load(String[] pageOrDir)
    {
        if (pageOrDir.length == 1) {
            load(pageOrDir[0], "");
        } else if (AppModule.DEV.equals(pageOrDir[1])) {
            // add specific page enabled by DEV mode
            if (!AppModule.isProduction) {
                load(pageOrDir[0], pageOrDir[1]);
            }
        } else if (pageOrDir.length==4) {
            load(pageOrDir[0], pageOrDir[3]);
        } else {
            load(pageOrDir[0], "");
        }
    }

    public void loadPageNames()
    {
        if (pageIcons == null) {
            pageIcons = new LinkedHashMap<String, String>();
            for (String[] pageOrDir : AppModule.LINK_PATH_PERMISSIONS) {
                if (pageOrDir != null) {
                    try {
                        if (pageOrDir.length > 3) {
                            if (securityService.hasPermission(pageOrDir[2])) {
                                load(pageOrDir);
                            }
                        } else {
                            load(pageOrDir);
                        }
                    } catch (Exception e) {
                        try {
                            load(pageOrDir);
                        } catch (Exception e2) {
                            try {
                                LOG.error("Failed to proceed to register pageOrDir " + pageOrDir[0]
                                        + " for permission " + pageOrDir[2]);
                            } catch (Exception e1) {
                                LOG.error("Failed to proceed to register pageOrDir " + pageOrDir[0]);
                            }
                            if (!AppModule.isProduction)
                                LOG.error("", e);
                        }
                    }
                }
            }
        }
        this.pageNames = new ArrayList<String>(pageIcons.keySet());
    }

    @Log
    @OnEvent(value = org.apache.tapestry5.EventConstants.ACTION, component = "logout")
    public String onActionFromLogout()
    {
        // Need to call this explicitly to invoke onlogout handlers (for remember me etc.)
        SecurityUtils.getSubject().logout();
        // http://shiro.apache.org/static/current/apidocs/org/apache/shiro/mgt/DefaultSecurityManager.html#stopSession%28org.apache.shiro.subject.Subject%29
        // stopSession(SecurityUtils.getSubject())
        try {
            // the session is already invalidated, but need to cause an exception since tapestry doesn't know about it
            // and you'll get a container exception message instead without this. Unfortunately, there's no way of
            // configuring Shiro to not invalidate sessions right now. See DefaultSecurityManager.logout()
            request.getSession(false).invalidate();
        } catch (Exception e) {
            LOG.debug("Invalidating HTTP session...", e);
        }
        return loginContextService.getLoginPage();
    }
}
