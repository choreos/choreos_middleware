package eu.choreos.nodepoolmanager.chef;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jclouds.chef.ChefContext;
import org.jclouds.chef.ChefContextFactory;


import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Files;
import com.google.inject.Module;

import eu.choreos.nodepoolmanager.Configuration;

public class ChefHelper {

    private static final String client = Configuration.get("CHEF_USER");
    private static final String pemFile = Configuration.get("CHEF_USER_KEY_FILE");
    private static ChefContext context;

    static {
        String credential = StringUtils.EMPTY;
        try {
            credential = Files.toString(new File(pemFile), Charsets.UTF_8);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Properties overrides = new Properties();
        overrides.setProperty("chef.endpoint", Configuration.get("CHEF_ENDPOINT"));
        context = new ChefContextFactory().createContext(client, credential, ImmutableSet.<Module> of(), overrides);
    }

    public static ChefContext getChefContext() {
        return context;
    }
}
