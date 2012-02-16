package eu.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import org.jclouds.chef.domain.Role;
import org.junit.Test;

import eu.choreos.nodepoolmanager.utils.ChefHelper;


public class ChefHelperTest {

    @Test
    public void testChefContext() throws Exception {
        Role role = ChefHelper.getChefContext().getApi().getRole("petals");
        assertEquals("petals", role.getName());
    }
}
