package org.ow2.choreos.chors.datamodel;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class LegacyServiceTest {

    private static final String URI1 = "uri1";
    private static final String URI2 = "uri2";
    
    private LegacyServiceSpec legacySpec;
    
    @Before
    public void setUp() throws Exception {
        List<String> uris = new ArrayList<String>();
        uris.add(URI1);
        uris.add(URI2);
        legacySpec = new LegacyServiceSpec();
        legacySpec.setName("service");
        legacySpec.setNativeURIs(uris);
    }

    @Test
    public void test() {
        LegacyService legacyService = new LegacyService(legacySpec);
        assertEquals(legacySpec, legacyService.getSpec());
        List<String> uris = legacyService.getUris();
        assertEquals(2, uris.size());
        assertTrue(uris.contains(URI1));
        assertTrue(uris.contains(URI2));
        List<LegacyServiceInstance> legacyInstances = legacyService.getLegacyServiceInstances();
        assertEquals(2, legacyInstances.size());
        assertEquals(URI1, legacyInstances.get(0).getUri());
        assertEquals(URI2, legacyInstances.get(1).getUri());
    }

}
