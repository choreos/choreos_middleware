package org.ow2.choreos.chors.bus;

import com.ebmwebsourcing.esstar.management.UserManagementClient;

public class LinagoraFactory {

    private static LinagoraFactory INSTANCE = new LinagoraFactory();

    public static LinagoraFactory getFactory() {
        return INSTANCE;
    }

    public UserManagementClient getUserManagementClient(String adminEndpoint) {
        return new UserManagementClient(adminEndpoint);
    }

}
