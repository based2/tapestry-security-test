// Copyright 2006-2013 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.apache.tapestry5.corelib.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractTextField;

/**
 * TextField component corresponds to {@code <input>} element. The value parameter will be edited (read when the containing
 * {@link Form} is rendered, and updated when the form is submitted). TextField
 * is generally used with string values, but other values are acceptable, as long as they can be freely converted back
 * and forth to strings.
 * <p/>
 * Includes the <code>size</code> attribute, if a {@link org.apache.tapestry5.beaneditor.Width} annotation is present on
 * the property bound to the value parameter.
 *
 * @bootstrap 3.0
 * @tapestrydoc
 */
public class TextField extends AbstractTextField
{
    /**
     * Sets the type attribute of the {@code <input>} element. The default is "text", but this can be overriden
     * when using <a href="http://www.w3.org/TR/html5/the-input-element.html">HTML5</a> types such as "number".
     */
    @Parameter(allowNull = false, value = "text", defaultPrefix = BindingConstants.LITERAL)
    private String type;

    // Color border effect on input focus
    // Values: [info|success|info|warning|danger]
    // @see org.apache.tapestry5.corelib.mixins.FormGroup
    @Parameter
    private String colorborder;

    // Label column/row size
    // @see org.apache.tapestry5.corelib.mixins.FormGroup
    @Parameter
    private String labelsize;

    // Input column/row size
    // @see org.apache.tapestry5.corelib.mixins.FormGroup
    @Parameter
    private String inputsize;

    // for label
    @Parameter
    private String forlabel;

    @Override
    protected void writeFieldTag(MarkupWriter writer, String value)
    {
        writer.element("input",

                "type", type,

                "name", getControlName(),

                "class", "form-control",

                "id", getClientId(),

                "value", value,

                "size", getWidth());
    }

    final void afterRender(MarkupWriter writer)
    {
        writer.end(); // input
    }

}
