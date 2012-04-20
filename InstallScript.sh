#!/bin/bash
#Usage:

#first get the source code from github 
	git clone git@github.com:felps/CHOReOS-Monitoring-Service.git

#then enter the folder:
	cd CHOReOS-Monitoring-Service

#Run maven's install script
	mvn install

#Run maven's package script
	mvn package

#Copy hostname.sh to current folder
	cp ChoreosMonitoring/src/main/resources/hostname.sh .

#run the executable jar
	java -jar glimpse/target/Glimpse-*-SNAPSHOT-jar-with-dependencies.jar

