package net.company.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Layout component for pages of application webapp.
 */
@Import(stylesheet={"context:bootstrap/css/bootstrap.css", "context:bootstrap/css/bootstrap-responsive.css"})
public class loginLayout
{
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
}
