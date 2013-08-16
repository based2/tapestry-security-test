package net.company.components;

import net.company.services.AppModule;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tynamo.security.services.SecurityService;

import java.util.ArrayList;
import java.util.List;

/**
 * Layout component for pages of application webapp.
 */

//@Exclude(stylesheet={"core"})  //If you do not want Tapestry CSS
@Import(stack="core",stylesheet={"context:styles/empty.css"})
public class Layout
{
    private final static Logger LOG = LoggerFactory.getLogger(Layout.class);

    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

    @Property
    private String pageName;

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

    @Persist
    private String[] accessiblePages;

    public String getClassForPageName()
    {
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
            for (String[] permission : AppModule.LINK_PATH_PERMISSIONS)
            {
                try {
                    if (securityService.hasPermission(permission[2])) {
                        pages.add(permission[0]);
                    }
                } catch (Exception e) {
                    pages.add(permission[0]);
                }
            }
            accessiblePages = pages.toArray(new String[pages.size()]);
        }
        return accessiblePages;
    }
}
