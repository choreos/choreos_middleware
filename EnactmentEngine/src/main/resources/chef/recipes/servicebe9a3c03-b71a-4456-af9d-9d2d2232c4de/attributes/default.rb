##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of be9a3c03-b71a-4456-af9d-9d2d2232c4de must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['be9a3c03-b71a-4456-af9d-9d2d2232c4de']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['be9a3c03-b71a-4456-af9d-9d2d2232c4de']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['be9a3c03-b71a-4456-af9d-9d2d2232c4de']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['be9a3c03-b71a-4456-af9d-9d2d2232c4de']['InstallationDir'] = ENV["HOME"]

