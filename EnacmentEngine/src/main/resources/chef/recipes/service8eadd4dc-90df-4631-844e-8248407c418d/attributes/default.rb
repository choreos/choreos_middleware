##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8eadd4dc-90df-4631-844e-8248407c418d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8eadd4dc-90df-4631-844e-8248407c418d']['InstallationDir'] = ENV["HOME"]

