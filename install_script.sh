#!/bin/sh

apt-get install ganglia-monitor
/etc/init.d/ganglia-monitor start

tar -xzf apache-activemq-5.5.1-bin.tar.gz
./apache-activem1-5.5.1/bin/activemq start

java -jar ThresholdDaemon.jar
java -jar glimpse.jar

#git clone git://github.com/felps/CHOReOS-Monitoring-Service.git
#cd CHOReOS-Monitoring-Service/ChoreosMonitoring
#mvn install
#cd target 
#java eu/choreos/monitoring/daemon/ThresholdEvalDaemon

