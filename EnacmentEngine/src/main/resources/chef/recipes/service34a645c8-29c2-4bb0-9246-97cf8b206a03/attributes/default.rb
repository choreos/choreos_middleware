##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 34a645c8-29c2-4bb0-9246-97cf8b206a03 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['34a645c8-29c2-4bb0-9246-97cf8b206a03']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['34a645c8-29c2-4bb0-9246-97cf8b206a03']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['34a645c8-29c2-4bb0-9246-97cf8b206a03']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['34a645c8-29c2-4bb0-9246-97cf8b206a03']['InstallationDir'] = ENV["HOME"]

