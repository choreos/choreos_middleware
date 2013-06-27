package org.ow2.choreos.services.datamodel;

public class FileNameRetriever {

    private DeployableServiceSpec spec;

    public FileNameRetriever(DeployableServiceSpec spec) {
        this.spec = spec;
    }

    public String getFileName() {
        // We assume that the packageUri ends with "/fileName.[war,jar]
        String fileName = "";
        String extension = spec.getPackageType().getExtension();
        String[] urlPieces = spec.getPackageUri().split("/");
        if (urlPieces[urlPieces.length - 1].contains("." + extension)) {
            fileName = urlPieces[urlPieces.length - 1];
        } else {
            throw new IllegalArgumentException();
        }
        return fileName;
    }

}
