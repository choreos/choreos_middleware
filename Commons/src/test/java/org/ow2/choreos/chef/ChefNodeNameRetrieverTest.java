package org.ow2.choreos.chef;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChefNodeNameRetrieverTest {

    @Test
    public void shouldRetrieveChefNodeNameFromBootstrapLog() {

	String chefName = "domU-12-31-39-0B-16-16.compute-1.internal";
	String bootstrapLog = "A lot of lines\n" + "Creating a new client identity for " + chefName
		+ " using the validator key.\n" + "and a lot of more lines.";

	ChefNodeNameRetriever retriever = new ChefNodeNameRetriever();
	String retrievedChefName = retriever.retrieveChefNodeNameFromBootstrapLog(bootstrapLog);
	assertEquals(chefName, retrievedChefName);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRetrieveChefNodeNameFromBootstrapLog() {

	String bootstrapLog = "A lot of lines\n" + "not a line with the chefName" + "and a lot of more lines.";

	ChefNodeNameRetriever retriever = new ChefNodeNameRetriever();
	retriever.retrieveChefNodeNameFromBootstrapLog(bootstrapLog);
    }

}
