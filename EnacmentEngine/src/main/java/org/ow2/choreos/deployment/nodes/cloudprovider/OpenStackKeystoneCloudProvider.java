package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.jclouds.Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

public class OpenStackKeystoneCloudProvider extends JCloudsCloudProvider {

    private static final String USER_NAME = "ubuntu";
    private static final String USER_PRIVATE_KEY = DeploymentManagerConfiguration.get("OPENSTACK_PRIVATE_SSH_KEY");
    private static final String IMAGE_ID = DeploymentManagerConfiguration.get("OPENSTACK_IMAGE_ID");
    private static final String DEFAULT_FLAVOR_NAME = "m1.tiny";

    private static final String OP_AUTHURL = DeploymentManagerConfiguration.get("OPENSTACK_IP");
    private static final String OP_TENANT = DeploymentManagerConfiguration.get("OPENSTACK_TENANT");
    private static final String OP_USER = DeploymentManagerConfiguration.get("OPENSTACK_USER");
    private static final String OP_PASS = DeploymentManagerConfiguration.get("OPENSTACK_PASSWORD");
    private static final String PROVIDER = "openstack-nova";
    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        PROPERTIES.setProperty(Constants.PROPERTY_ENDPOINT, OP_AUTHURL);
    }

    public OpenStackKeystoneCloudProvider() {
        super(OP_TENANT + ":" + OP_USER, OP_PASS, PROVIDER, PROPERTIES);
    }

    public String getCloudProviderName() {
        return "OpenStack Keystone Provider";
    }

    public Image getImage() {
        ComputeService client = getComputeService();
        Set<? extends Image> images = client.listImages();

        if (images == null || images.isEmpty())
            throw new IllegalStateException("No images available!");

        for (Image image : images) {
            if (image.getId().equals(IMAGE_ID))
                return image;
        }

        return images.iterator().next();
    }

    public Hardware getHardwareProfile() {
        ComputeService client = getComputeService();
        Set<? extends Hardware> profiles = client.listHardwareProfiles();

        if (profiles == null || profiles.isEmpty())
            throw new IllegalStateException("No hardware profiles available!");

        for (Hardware profile : profiles) {
            if (profile.getName().equals(DEFAULT_FLAVOR_NAME))
                return profile;
        }

        Iterator<? extends Hardware> it = profiles.iterator();
        return it.next();
    }

    @Override
    protected String getDefaultImageId() {
        return IMAGE_ID;
    }

    @Override
    protected String getHardwareId() {
        String flavorName = DeploymentManagerConfiguration.get("OPENSTACK_FLAVOR_NAME");
        if (flavorName == null || flavorName.isEmpty())
            flavorName = DEFAULT_FLAVOR_NAME;
        for (Hardware profile : getComputeService().listHardwareProfiles()) {
            if (profile.getName().equals(flavorName)) {
                return profile.getId();
            }
        }
        return getComputeService().listHardwareProfiles().iterator().next().getId();
    }

    @Override
    protected String getUserName() {
        return USER_NAME;
    }

    @Override
    protected String getUserPrivateKey() {
        return USER_PRIVATE_KEY;
    }

    @Override
    protected void configureTemplateOptions(TemplateOptions templateOptions) {
        NovaTemplateOptions options = templateOptions.as(NovaTemplateOptions.class);
        options.keyPairName(DeploymentManagerConfiguration.get("OPENSTACK_KEY_PAIR"));
        options.securityGroupNames("default");
    }

    @Override
    protected String getNodeIp(NodeMetadata nodeMetadata) {
        Iterator<String> privateAddresses = nodeMetadata.getPrivateAddresses().iterator();
        if (privateAddresses != null && privateAddresses.hasNext()) {
            return privateAddresses.next();
        } else {
            throw new IllegalStateException("Could not retrieve IP from node");
        }
    }
}