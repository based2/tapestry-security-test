// Copyright 2013 The Apache Software Foundation
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
package org.apache.tapestry5.corelib.mixins;

import org.apache.tapestry5.Field;
import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.ValidationDecorator;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.internal.util.InternalUtils;

/**
* Applied to a {@link org.apache.tapestry5.Field}, this provides the outer layers of markup to correctly
* render text fields, selects, and textareas using Bootstrap:
* an outer {@code <div class="field-group">}, a {@code <label>}, and a
* {@code <div class="controls">} around the field itself. This control is not appropriate
* for radio buttons or check boxes, as those want to have the label element directly around the control.
* As with the {@link org.apache.tapestry5.corelib.components.Label} component, the {@code for} attribute is set (after the field itself
* renders).
* <p/>
* This component is not appropriate for radio buttons or checkboxes as they use a different class on the outermost element
* ("radio" or "checkbox") and next the element inside the {@code <label>}.
*
* @bootstrap 3.0
* @tapestrydoc
* @since 5.4
* note: http://tawus.wordpress.com/2011/08/01/tapestry-mixins-classtransformations/
*/
public class FormGroup
 {

  // Color border effect on input focus
  // Values: [info|success|info|warning|danger]
  @BindParameter //(value="info")
  private String colorborder;

  // Label column/row size
  @BindParameter //(value="col-lg-2")  // for a parent form with class="form-horizontal"
  private String labelsize;

  // Input column/row size
  @BindParameter //(value="col-lg-6")
  private String inputsize;

  // for label
  @BindParameter
  private String forlabel;

  @InjectContainer
  private Field field;

  private Element _label;

  @Environmental
  private ValidationDecorator decorator;

  private void createWithClass(MarkupWriter writer, String tag, String init, String toAdd)
  {
      if (!InternalUtils.isBlank(toAdd)) {
          init += " " + toAdd;
      }
      writer.element(tag, "class", init);
  }

  void beginRender(MarkupWriter writer)
  {
    createWithClass(writer, "div", "form-group", colorborder);

    decorator.beforeLabel(field);

    createWithClass(writer, "label", "control-label", labelsize);

    writer.end();  // label.control-label

    if (!InternalUtils.isBlank(inputsize)) {
        writer.element("div", "class", inputsize);
    }

    if (_label!=null) {
        fillInLabelAttributes();
    }

    decorator.afterLabel(field);
  }

  @HeartbeatDeferred
  void fillInLabelAttributes()
  {
    String _for = field.getClientId();
    if (!InternalUtils.isBlank(_for)) {
        _for = forlabel;
    }
    _label.attribute("for", _for);
    _label.text(field.getLabel());
  }

  void afterRender(MarkupWriter writer)
  {
    if (!InternalUtils.isBlank(inputsize)) {
       writer.end(); // div.inputsize
    }
    writer.end(); // div.form-group
  }
}