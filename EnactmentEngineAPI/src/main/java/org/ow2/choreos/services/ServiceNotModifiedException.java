/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services;

public class ServiceNotModifiedException extends ServiceDeployerException {

    private static final long serialVersionUID = 8106574276166921975L;

    public ServiceNotModifiedException(String serviceUuid) {
        super("Could not modify service " + serviceUuid);
    }

}
