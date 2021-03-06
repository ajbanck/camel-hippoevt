/*
 * Copyright 2014-2014 Hippo B.V. (http://www.onehippo.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *         http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onehippo.forge.camel.component.hippo;

import net.sf.json.JSONObject;

import org.apache.camel.impl.DefaultMessage;
import org.apache.camel.util.ObjectHelper;

public class HippoEventMessage extends DefaultMessage {

    public HippoEventMessage(final JSONObject eventJson) {
        setBody(eventJson);
    }

    @Override
    public String toString() {
        if (getBody() != null) {
            return "HippoEventMessage[event: " + getBody() + "]";
        }

        return "HippoEventMessage@" + ObjectHelper.getIdentityHashCode(this);
    }

    @Override
    public void copyFrom(org.apache.camel.Message that) {
        if (that == this) {
            // the same instance so do not need to copy
            return;
        }

        // must initialize headers before we set the JmsMessage to avoid Camel
        // populating it before we do the copy
        getHeaders().clear();

        // copy body and fault flag

        Object body = that.getBody();

        if (body != null && body instanceof JSONObject) {
            setBody(JSONObject.fromObject((JSONObject) body));
        } else {
            setBody(body);
        }

        setFault(that.isFault());

        // we have already cleared the headers
        if (that.hasHeaders()) {
            getHeaders().putAll(that.getHeaders());
        }

        getAttachments().clear();

        if (that.hasAttachments()) {
            getAttachments().putAll(that.getAttachments());
        }
    }
}
