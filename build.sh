mvn clean javadoc:jar source:jar install -Dmaven.test.skip=true
mvn eclipse:clean eclipse:eclipse
cp $HOME/.m2/repository/eu/choreos/rehearsal/0.13/rehearsal-0.13.jar EnactmentEngine/lib/
