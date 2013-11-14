##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8051e711-780c-4126-8645-6612decf3508 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8051e711-780c-4126-8645-6612decf3508']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['8051e711-780c-4126-8645-6612decf3508']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8051e711-780c-4126-8645-6612decf3508']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8051e711-780c-4126-8645-6612decf3508']['InstallationDir'] = ENV["HOME"]

