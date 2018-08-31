package net.company.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.annotations.Import;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.annotations.Property;

/**
 * Layout component for login application page
 */
@Import(stack="core",
        // pkdvc - styles/pixel-kit/dark-velvet-css/
        /*library={"context:pkdvc/js/libs/modernizr.min.js",
                "context:pkdvc/js/libs/jquery-1.10.0.js",
                "context:pkdvc/js/libs/jquery-ui.min.js",
                "context:pkdvc/js/libs/bootstrap.min.js",
                "context:pkdvc/js/general.js"
        },*/
        stylesheet={"context:styles/bootstrap-bootswatch-slate.css",
                "context:styles/empty.css","context:styles/codepen_yez.css"}
        //stylesheet={"context:pkdvc/css/bootstrap.css",
                //"context:styles/pixel-kit/dark-velvet-css/css/jquery-ui-1.8.20.custom.css",
        //        "context:pkdvc/style.css"}
)
public class LoginLayout {
    /** The page title, for the <title> element and the <h1> element. */
    @Property
    @Parameter(required = true, defaultPrefix = BindingConstants.LITERAL)
    private String title;
}
