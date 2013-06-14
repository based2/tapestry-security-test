package net.company.components;

/**
 * Layout component for pages of application webapp.
 */
@org.apache.tapestry5.annotations.Import(library={"context:layout/layout.css"})
public class Layout
{
    /** The page title, for the <title> element and the <h1> element. */
    @org.apache.tapestry5.annotations.Property
    @org.apache.tapestry5.annotations.Parameter(required = true, defaultPrefix = org.apache.tapestry5.BindingConstants.LITERAL)
    private String title;

    @org.apache.tapestry5.annotations.Property
    private String pageName;

    @org.apache.tapestry5.annotations.Property
    @org.apache.tapestry5.annotations.Parameter(defaultPrefix = org.apache.tapestry5.BindingConstants.LITERAL)
    private String sidebarTitle;

    @org.apache.tapestry5.annotations.Property
    @org.apache.tapestry5.annotations.Parameter(defaultPrefix = org.apache.tapestry5.BindingConstants.LITERAL)
    private org.apache.tapestry5.Block sidebar;

    @org.apache.tapestry5.ioc.annotations.Inject
    private org.apache.tapestry5.ComponentResources resources;

    public String getClassForPageName()
    {
      return resources.getPageName().equalsIgnoreCase(pageName)
             ? "current_page_item"
             : null;
    }

    public String[] getPageNames()
    {
      return new String[] { "Index", "About", "Contact", "Logout", "junior/Index", "senior/Index", "admin/Index" };
    }
}
