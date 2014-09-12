// Copyright 2014 The Apache Software Foundation
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package net.company.components;

import org.apache.tapestry5.BindingConstants;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

/**
 * Icon component for Font Awesome
 * Renders a {@code <span>} tag with the CSS class
 * to select a <a href="http://fontawesome.io/examples/">Font Awesome</a>.
 *
 * @tapestrydoc
 */
public class Fa
{
    /**
     * The name of the icon, e.g., "arrow-up", "flag", "fire" etc.
     */
    @Parameter(required=true, allowNull=false, cache=true, defaultPrefix=BindingConstants.LITERAL)
    private String icon;

    boolean beginRender(MarkupWriter writer)
    {
        writer.element("i", "class", "fa fa-" + icon);
        writer.end();

        return false;
    }
}
