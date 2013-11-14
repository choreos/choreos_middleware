##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7563c81c-55bf-4531-91de-1872166c2c3d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7563c81c-55bf-4531-91de-1872166c2c3d']['InstallationDir'] = ENV["HOME"]

