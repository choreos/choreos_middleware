package org.ow2.choreos.chors.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

public class LegacyContextCasterTest {

    private static final String BANK_URI = "http://future_bank.uk/ws";
    private static final String BANK_NAME = "future_bank";
    private static final String BANK_ROLE = "bank";
    
    private static final String CENTRAL_BANK_URI = "http://central_bank.uk/ws";
    private static final String CENTRAL_BANK_NAME = "central_bank";
    private static final String CENTRAL_BANK_ROLE = "bank";

    private ModelsForTest models;
    private LegacyServiceSpec bankSpec, centralBankSpec;
    private LegacyService bankService, centralBankService;
    private DeployableServiceSpec travelSpec;
    private DeployableService travelService;
    private ContextSender senderMock;

    @Before
    public void setUp() {
        LogConfigurator.configLog();
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        travelSpec = models.getTravelSpec();
        travelService = models.getTravelService();
        createdBank();
        configureMock();
        createdCentralBank();
    }

    private void createdBank() {
        bankSpec = new LegacyServiceSpec();
        bankSpec.setNativeURIs(Collections.singletonList(BANK_URI));
        bankSpec.setName(BANK_NAME);
        bankSpec.setRoles(Collections.singletonList(BANK_ROLE));
        bankSpec.setServiceType(ServiceType.SOAP);
        bankService = new LegacyService(bankSpec);
        models.getChorSpec().addServiceSpec(bankSpec);
        models.getChoreography().addService(bankService);
    }
    
    private void createdCentralBank() {
        centralBankSpec = new LegacyServiceSpec();
        centralBankSpec.setNativeURIs(Collections.singletonList(CENTRAL_BANK_URI));
        centralBankSpec.setName(CENTRAL_BANK_NAME);
        centralBankSpec.setRoles(Collections.singletonList(CENTRAL_BANK_ROLE));
        centralBankSpec.setServiceType(ServiceType.SOAP);
        centralBankService = new LegacyService(centralBankSpec);
        models.getChorSpec().addServiceSpec(centralBankSpec);
        models.getChoreography().addService(centralBankService);
    }

    private void createDependencyFromDeployableToLegacy() {
        travelSpec.setDependencies(Collections.singletonList(new ServiceDependency(BANK_NAME, BANK_ROLE)));
    }

    private void createDependencyFromLegacyToService() {
        String travelRole = travelSpec.getRoles().get(0);
        bankSpec.setDependencies(Collections.singletonList(new ServiceDependency(travelSpec.getName(), travelRole)));
    }
    
    private void createDependencyFromLegacyToLegacy() {
        bankSpec.setDependencies(Collections.singletonList(new ServiceDependency(CENTRAL_BANK_NAME, CENTRAL_BANK_ROLE)));
    }

    private void configureMock() {
        senderMock = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = senderMock;
    }

    @Test
    public void deployableServiceShouldReceiveLegacyService() throws ContextNotSentException {
        createDependencyFromDeployableToLegacy();
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();
        String travelUri = travelService.getUris().get(0);
        verify(senderMock).sendContext(travelUri, BANK_ROLE, BANK_NAME, bankService.getUris());
    }

    @Test
    public void legacyServiceShouldReceiveDeployable() throws ContextNotSentException {
        createDependencyFromLegacyToService();
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();
        String travelRole = travelSpec.getRoles().get(0);
        verify(senderMock).sendContext(BANK_URI, travelRole, travelSpec.getName(), travelService.getUris());
    }
    
    @Test
    public void legacyServiceShouldReceiveLegacy() throws ContextNotSentException {
        createDependencyFromLegacyToLegacy();
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();
        List<String> centralBankUris = Collections.singletonList(CENTRAL_BANK_URI);
        verify(senderMock).sendContext(BANK_URI, CENTRAL_BANK_ROLE, CENTRAL_BANK_NAME, centralBankUris);
    }

    @After
    public void tearDown() {
        ContextSenderFactory.testing = false;
    }
}
