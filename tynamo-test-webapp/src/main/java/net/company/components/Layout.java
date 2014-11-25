package net.company.components;

import net.company.services.AppModule;
import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;
import org.slf4j.Logger;

/**
 * Layout component for generic application's pages
 * <p/>
 * todo update layout template, bootswatch.css, and icon config in AppModule and fix it
 */
// http://getbootstrap.com/examples/theme/
@Import(stack = "core",
        stylesheet = {"context:styles/bootstrap-bootswatch-slate.css",
                "context:font-awesome-4.2.0/css/font-awesome.min.css",
                "context:styles/empty.css"})
public class Layout
{

    /**
     * Page title, for the <title> element and the <h1> element.
     */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;

   // @Property
   // private String pageName;

    @Component(id="navbarapp")//(parameters={"subject=prop:panelTitle", "closed=true"}, publishParameters = "options, closed")
    private Navbar navbarapp;

    @Inject
    private Logger LOG;

    @Inject
    private ComponentResources resources;

    @SetupRender
    public void init()
    {
        // todo navbar component update via parameter - remove Navbar.getPage/setPage
        if (InternalUtils.isBlank(navbarapp.getPage())) {
            navbarapp.setPage(AppModule.URL_SUCCESS.substring(1));
        }
        if (LOG.isDebugEnabled()) {
            try {
               LOG.debug(resources.getPageName().toLowerCase() + " / " + navbarapp.getPage().toLowerCase());
            } catch (Exception e) {
                LOG.error(resources.getPageName(), e);
            }
        }
    }
}
