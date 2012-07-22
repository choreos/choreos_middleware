mvn clean javadoc:jar source:jar install -Dmaven.test.skip=true
mvn eclipse:clean eclipse:eclipse
