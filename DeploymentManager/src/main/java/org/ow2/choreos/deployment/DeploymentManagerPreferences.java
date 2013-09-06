package org.ow2.choreos.deployment;

import java.io.IOException;
import java.util.prefs.Preferences;

import org.apache.commons.lang.SerializationUtils;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class DeploymentManagerPreferences {

    Preferences prefs;

    private static DeploymentManagerPreferences INSTANCE = null;

    private DeploymentManagerPreferences() {
	prefs = Preferences.userNodeForPackage(getClass());
    }

    public Preferences getPrefs() {
	return prefs;
    }

    public static synchronized DeploymentManagerPreferences getInstance() {
	if (INSTANCE == null)
	    INSTANCE = new DeploymentManagerPreferences();
	return INSTANCE;
    }

    public void putObject(String key, CloudNode node) throws IOException {
	prefs.putByteArray(key, SerializationUtils.serialize(node));

    }

    public CloudNode getObject(String key) throws IOException, ClassNotFoundException {
	return (CloudNode) SerializationUtils.deserialize(prefs.getByteArray(key,
		SerializationUtils.serialize(new CloudNode())));

    }

}
