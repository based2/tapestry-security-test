package net.company.components;

import net.company.services.AppModule;
import net.company.services.NavbarAccess;
import org.apache.shiro.SecurityUtils;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.apache.tapestry5.services.Request;
import org.slf4j.Logger;
import org.tynamo.security.internal.services.LoginContextService;
import org.tynamo.security.services.SecurityService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Horizontal menu with icons
 * Icons and order is Configurable in @NavbarAccessImpl MODES_PAGES_PATHS_PERMISSIONS_ICONS
 */
public class Navbar {

    @Parameter(required = true, cache=true, defaultPrefix = BindingConstants.LITERAL)
    @Property
    private String pageName;

    @Property
    @Persist
    private List<String> pageNames;

    @Property
    private String name;

    @Property
    private String icon;

    @Property
    @Persist
    //@ImmutableSessionPersistedObject http://tapestry.apache.org/persistent-page-data.html#PersistentPageData-ClusteringIssues
    private String uname;

    @Inject
    private Logger LOG;

    @Inject
    private Request request;

    @Inject
    @Property
    private SecurityService securityService;

    @Inject
    private LoginContextService loginContextService;

    @Inject
    private ComponentResources resources;

    @Inject
    private NavbarAccess navbarAccess;

    private Map<String, String> pageIcons;

    @SetupRender
    public void init() {
        pageIcons = navbarAccess.getPageNames(securityService);
        pageNames = new ArrayList<>(pageIcons.keySet());
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
    public String getClassForPageName() {
        if (InternalUtils.isBlank(uname)) uname = (String) SecurityUtils.getSubject().getPrincipal();

        if (InternalUtils.isBlank(name)) {
            name = AppModule.URL_SUCCESS.substring(1);
            this.init();
        }

        if (InternalUtils.isBlank(icon)) icon = this.loadIcon(icon);

        try {
            if (LOG.isDebugEnabled()) LOG.debug("test:"
                    + resources.getPageName().toLowerCase() + " / " + name.toLowerCase());
            return resources.getPageName().toLowerCase().startsWith(name.toLowerCase()) ? "active" : null;
        } catch (Exception e) {
            LOG.error(resources.getPageName(), e);
            return null;
        }
    }

    public String loadIcon() {
        if (pageIcons == null) init();
        return pageIcons.get(name);
    }

    public boolean isIcon() {
        if (pageIcons == null) init();
        icon = loadIcon();
        return icon!=null;
    }

    /** To Component @net.company.components.Layout */
    public String getPage()
    {
        return pageName;
    }

    /** From Component @net.company.components.Layout */
    public void setPage(String pageName) {
        this.pageName = pageName;
        this.name = pageName;
    }

    public String loadIcon(String pageName) {
        if (pageIcons == null) init();
        return pageIcons.get(pageName);
    }

    @OnEvent(value = org.apache.tapestry5.EventConstants.ACTION, component = "logout")
    public String onActionFromLogout() {
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
            LOG.debug("Invalidating HTTP session...");
        }
        return loginContextService.getLoginURL();
    }
}
