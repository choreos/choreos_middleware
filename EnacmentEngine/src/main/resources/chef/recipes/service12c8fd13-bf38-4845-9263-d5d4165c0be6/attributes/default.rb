##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 12c8fd13-bf38-4845-9263-d5d4165c0be6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['WarFile'] = ""
default['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['12c8fd13-bf38-4845-9263-d5d4165c0be6']['InstallationDir'] = ENV["HOME"]

