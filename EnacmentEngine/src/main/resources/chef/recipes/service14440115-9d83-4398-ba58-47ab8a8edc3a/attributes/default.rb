##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 14440115-9d83-4398-ba58-47ab8a8edc3a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['14440115-9d83-4398-ba58-47ab8a8edc3a']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['14440115-9d83-4398-ba58-47ab8a8edc3a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['14440115-9d83-4398-ba58-47ab8a8edc3a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['14440115-9d83-4398-ba58-47ab8a8edc3a']['InstallationDir'] = ENV["HOME"]

