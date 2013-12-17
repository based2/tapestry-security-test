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
 *
 * todo update layout template, bootswatch.css, and icon config in AppModule and fix it
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
    @Persist //@ImmutableSessionPersistedObject http://tapestry.apache.org/persistent-page-data.html#PersistentPageData-ClusteringIssues
    private String uname;

    @Inject
    private Request request;

    @Inject
    private LoginContextService loginContextService;

    @Persist
    //private String[] accessiblePages;
    private List<String> accessiblePages;

    @Persist
    //private String[] icons;
    private List<String> icons;
    //@Persist  Map?

    // todo direct pageName/icon map
    private String loadIcon(){
        if (accessiblePages==null) {
            loadPageNames();
        }

        for(int i = 0; i < accessiblePages.size(); i++){
            try {
                if (pageName.equals(accessiblePages.get(i))) {
                    return icons.get(i);
                }
            } catch (Exception e) { return null;}
        }
        return null;
    }

    public boolean isIcon()
    {
        if (accessiblePages==null) {
            loadPageNames();
        }
    /* if (icons==null) {
        icons = new String[accessiblePages.length];
    }*/
        for(int i = 0; i < accessiblePages.size(); i++){
            try {
                if (pageName.equals(accessiblePages.get(i))) {
                    if (icons.get(i)!=null) {
                        icon = icons.get(i);
                        return true;
                    }  else  {
                        return false;
                    }
                }
            } catch (Exception e) { return false;}
        }
        return false;
    }

    /**
     * Init current navbar with its active state
     *
     * <pre>
     *    <t:loop class="prop:classForPageName()">
     * </pre>
     * @return active | null
     */
    public String getClassForPageName()
    {
        if (uname==null) {
            uname = (String) SecurityUtils.getSubject().getPrincipal();
        }
        if (pageName==null) {
            pageName = AppModule.URL_SUCCESS.substring(1);
            this.loadPageNames();
        }

        if (icon==null) {
            icon = this.loadIcon();
        }
        try {
            if (LOG.isDebugEnabled()) {
                LOG.debug(resources.getPageName().toLowerCase() + " / " + pageName.toLowerCase());
            }
            return resources.getPageName().toLowerCase().startsWith(pageName.toLowerCase())
                    ? "active"
                    : null;

        } catch (Exception e){
            LOG.error(resources.getPageName(), e);
            return null;
        }
    }

    private void loadIcon(String[] pageOrDir)
    {
        try {
            icons.add(pageOrDir[3]);

        } catch (Exception e){}
    }

    private void load(String[] pageOrDir)
    {
        if (pageOrDir.length==1) {
            this.accessiblePages.add(pageOrDir[0]);
            loadIcon(pageOrDir);
        } else if (AppModule.DEV.equals(pageOrDir[1])) {
            // add specific page enabled by DEV mode
            if (!AppModule.isProduction) {
                this.accessiblePages.add(pageOrDir[0]);
                loadIcon(pageOrDir);
            }
        } else {
            this.accessiblePages.add(pageOrDir[0]);
            loadIcon(pageOrDir);
        }
    }

    public void loadPageNames()
    {
        if (accessiblePages==null) {
            accessiblePages = new ArrayList<String>();
            icons = new ArrayList<String>();
            for (String[] pageOrDir : AppModule.LINK_PATH_PERMISSIONS)
            {
                if (pageOrDir!=null){
                    try {
                        if (pageOrDir.length>3){
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
                                LOG.error("Failed to proceed to register pageOrDir "+ pageOrDir[0]
                                        + " for permission " + pageOrDir[2]);
                            } catch (Exception e1) {
                                LOG.error("Failed to proceed to register pageOrDir " + pageOrDir[0]);
                            }
                            if (!AppModule.isProduction)
                                LOG.error("",e);
                        }
                    }
                }
            }

            //  String[] i = Objects.toString(icos);
            //  icons2 = i;
            // icons = ArrayUtils.toArray(icos);
            //icons = new String[icos.size()];
            //icos.toArray(icons);
            //icons = icos.toArray(new String[icos.size()]);
            ///*String[]*/ p = Objects.toString(pages);

            //accessiblePages = p;
            //accessiblePages = ArrayUtils.toArray(pages);
            //accessiblePages = new String[pages.size()];
            //pages.toArray(accessiblePages);
            //accessiblePages = pages.toArray(new String[pages.size()]);
        }
        this.pageNames = this.accessiblePages;
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
            request.getSession(false).invalidate();
        } catch (Exception e) {
            LOG.error("Invalidating HTTP session...");

        }
        return loginContextService.getLoginPage();
    }
}
