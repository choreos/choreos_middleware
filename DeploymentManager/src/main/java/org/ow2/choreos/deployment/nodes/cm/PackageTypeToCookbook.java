package org.ow2.choreos.deployment.nodes.cm;

import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.utils.Configuration;

/**
 * 
 * @author leonardo
 *
 */
public class PackageTypeToCookbook {

    private static final String FILE_PATH = "cookbooks.properties";
    
    private final Configuration configuration;

    private static PackageTypeToCookbook INSTANCE = new PackageTypeToCookbook();
    
    private PackageTypeToCookbook() {
        this.configuration = new Configuration(FILE_PATH);
    }

    public static String getCookbookName(PackageType packageType) {
        return getCookbookName(packageType.toString());
    }    
    
    public static String getCookbookName(String packageType) {
        String value = INSTANCE.configuration.get(packageType);
        if (value == null)
            throw new IllegalArgumentException();
        return value.trim();
    }

    public static void set(String key, int value) {
        INSTANCE.configuration.set(key, Integer.toString(value));
    }
    
}
