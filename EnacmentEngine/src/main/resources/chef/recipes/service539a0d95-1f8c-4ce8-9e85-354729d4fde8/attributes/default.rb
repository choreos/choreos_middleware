##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 539a0d95-1f8c-4ce8-9e85-354729d4fde8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['539a0d95-1f8c-4ce8-9e85-354729d4fde8']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['539a0d95-1f8c-4ce8-9e85-354729d4fde8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['539a0d95-1f8c-4ce8-9e85-354729d4fde8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['539a0d95-1f8c-4ce8-9e85-354729d4fde8']['InstallationDir'] = ENV["HOME"]

