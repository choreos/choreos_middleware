##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2b9c2e8c-d017-462b-b612-3014d25f7d04 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2b9c2e8c-d017-462b-b612-3014d25f7d04']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['2b9c2e8c-d017-462b-b612-3014d25f7d04']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2b9c2e8c-d017-462b-b612-3014d25f7d04']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2b9c2e8c-d017-462b-b612-3014d25f7d04']['InstallationDir'] = ENV["HOME"]

