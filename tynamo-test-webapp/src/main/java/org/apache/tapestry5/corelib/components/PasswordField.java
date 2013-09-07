// Copyright 2007-2013 The Apache Software Foundation
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

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.corelib.base.AbstractTextField;

/**
 * A version of {@link TextField}, but rendered out as an &lt;input type="password"&gt; element. Further, the output
 * value for a PasswordField is always blank.  When the value provided to the PasswordField is blank, it does not update
 * its property (care should be taken that the "required" validator not be used in that case).
 * <p/>
 * Includes the <code>size</code> attribute, if a {@link org.apache.tapestry5.beaneditor.Width} annotation is present on
 * the property bound to the value parameter.
 *
 * @bootstrap 3.0
 * @tapestrydoc
 * @see Form
 * @see TextField
 * @see TextArea
 */
public class PasswordField extends AbstractTextField
{
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
    protected final void writeFieldTag(MarkupWriter writer, String value)
    {
        writer.element("input",

                "type", "password",

                "class", "form-control",

                "name", getControlName(),

                "id", getClientId(),

                "value", "",

                "size", getWidth());
    }


    final void afterRender(MarkupWriter writer)
    {
        writer.end(); // input
    }

    /**
     * Returns true, blank input should be ignored and not cause an update to the server-side property bound to the
     * value parameter.
     *
     * @return true
     */
    @Override
    protected boolean ignoreBlankInput()
    {
        return true;
    }
}
