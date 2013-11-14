##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a705d686-a8be-41b9-9bba-5f1c8366d13c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a705d686-a8be-41b9-9bba-5f1c8366d13c']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['a705d686-a8be-41b9-9bba-5f1c8366d13c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a705d686-a8be-41b9-9bba-5f1c8366d13c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a705d686-a8be-41b9-9bba-5f1c8366d13c']['InstallationDir'] = ENV["HOME"]

