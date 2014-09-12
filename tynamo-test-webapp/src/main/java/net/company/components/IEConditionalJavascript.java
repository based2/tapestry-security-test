package net.company.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

/**
 * IEConditionalJavascript
 * A component that wraps its body in an IE conditional comment.
 *
 * <pre>
 *     <t:conditionalcomment condition="lte IE 9">
 *        <t:remove>
 *           Fix IE8+9 lack of support for html5 elements and media queries
 *        </t:remove>
 *        <script src="${context:js/html5shiv.js}" type="text/javascript"/>
 *        <script src="${context:js/respond.js}" type="text/javascript"/>
 *        <script src="${context:js/media.match.js}" type="text/javascript"/>
 *      </t:conditionalcomment>
 * </pre>
 *
 * @author Howard Lewis Ship
 * @date 20/06/2014 11:16
 *
 * http://tapestryjava.blogspot.be/2013/12/tapestry-quicky-conditionalcomment.html
 */
public class IEConditionalJavascript {

    @Parameter(required = true, allowNull = false, defaultPrefix = BindingConstants.LITERAL)
    private String condition;

    void beginRender(MarkupWriter writer) {
        writer.writeRaw(String.format("<!--[if %s]>", condition));
    }

    void afterRender(MarkupWriter writer) {
        writer.writeRaw("<![endif]-->");
    }
}
