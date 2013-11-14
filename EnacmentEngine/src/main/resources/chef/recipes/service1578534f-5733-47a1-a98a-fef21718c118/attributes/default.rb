##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1578534f-5733-47a1-a98a-fef21718c118 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1578534f-5733-47a1-a98a-fef21718c118']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1578534f-5733-47a1-a98a-fef21718c118']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1578534f-5733-47a1-a98a-fef21718c118']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1578534f-5733-47a1-a98a-fef21718c118']['InstallationDir'] = ENV["HOME"]

