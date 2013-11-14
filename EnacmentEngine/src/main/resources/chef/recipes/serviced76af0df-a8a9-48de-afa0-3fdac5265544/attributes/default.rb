##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d76af0df-a8a9-48de-afa0-3fdac5265544 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d76af0df-a8a9-48de-afa0-3fdac5265544']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['d76af0df-a8a9-48de-afa0-3fdac5265544']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d76af0df-a8a9-48de-afa0-3fdac5265544']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d76af0df-a8a9-48de-afa0-3fdac5265544']['InstallationDir'] = ENV["HOME"]

