package org.ow2.choreos.chors.bus;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ebmwebsourcing.esstar.management.UserManagementClient;

public class EasyESBNodeImplTest {

    private static final String PUBLIC_IP = "54.10.15.1";
    private static final String PRIVATE_IP = "10.0.0.2";
    private static final String ADMIN_ENDPOINT = "http://" + PUBLIC_IP + ":8180/services/adminExternalEndpoint";
    // how it is returned by linagora api:
    private static final String PROXIFIED_ADDRESS = "http://" + PRIVATE_IP + ":8180/services/AirlineServicePortClientProxyEndpoint";
    // how it must be saved by us, so we can access the service:
    private static final String EXPECTED_PROXIFIED_ADDRESS = "http://" + PUBLIC_IP + ":8180/services/AirlineServicePortClientProxyEndpoint";

    private LinagoraFactory linagoraFactoryMock;
    
    @Before
    public void setUp() throws Exception {
        UserManagementClient userManagementClientMock = mock(UserManagementClient.class);
        when(userManagementClientMock.proxify(any(String.class), any(String.class))).thenReturn(PROXIFIED_ADDRESS);
        linagoraFactoryMock = mock(LinagoraFactory.class);
        when(linagoraFactoryMock.getUserManagementClient(any(String.class))).thenReturn(userManagementClientMock);
    }

    @Test
    public void shouldReturnValidProxifiedAddress() throws EasyESBException {
        EasyESBNode esb = new EasyESBNodeImpl(ADMIN_ENDPOINT, linagoraFactoryMock);
        String serviceUri = "http://10.0.0.3/airline";
        String proxifiedAddress = esb.proxifyService(serviceUri, serviceUri + "?wsdl");
        assertEquals(EXPECTED_PROXIFIED_ADDRESS, proxifiedAddress);
    }

}
