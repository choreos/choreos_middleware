##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ee9bdeee-ed9c-44ca-8d4c-3bb632248509 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ee9bdeee-ed9c-44ca-8d4c-3bb632248509']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['ee9bdeee-ed9c-44ca-8d4c-3bb632248509']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ee9bdeee-ed9c-44ca-8d4c-3bb632248509']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ee9bdeee-ed9c-44ca-8d4c-3bb632248509']['InstallationDir'] = ENV["HOME"]

