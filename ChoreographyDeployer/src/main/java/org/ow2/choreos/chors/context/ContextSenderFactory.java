/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.utils.Configuration;


public class ContextSenderFactory {

    public static boolean testing = false;
    public static ContextSender senderForTesting;

    private static final String CLASS_MAP_FILE_PATH = "context_sender.properties";

    private static Configuration classMap;

    static {
        classMap = new Configuration(CLASS_MAP_FILE_PATH);
    }

    public static ContextSender getNewInstance(ServiceType serviceType) {
        if (testing)
            return senderForTesting;
        else
            return getNewInstance(serviceType.toString());
    }

    public static ContextSender getNewInstance(String serviceType) {
        if (testing)
            return senderForTesting;
        String className = classMap.get(serviceType);
        ContextSender contextSender = null;
        try {
            @SuppressWarnings("unchecked")
            // catches handle the problem
            Class<ContextSender> clazz = (Class<ContextSender>) Class.forName(className);
            contextSender = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            creationFailed(serviceType);
        } catch (InstantiationException e) {
            creationFailed(serviceType);
        } catch (IllegalAccessException e) {
            creationFailed(serviceType);
        } catch (ClassCastException e) {
            creationFailed(serviceType);
        }
        return contextSender;
    }

    private static void creationFailed(String type) {
        throw new IllegalStateException("Invalid Service Type: " + type);
    }

}
