##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 37b587da-2bc0-43f0-bbde-9f2c97192923 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['37b587da-2bc0-43f0-bbde-9f2c97192923']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['37b587da-2bc0-43f0-bbde-9f2c97192923']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['37b587da-2bc0-43f0-bbde-9f2c97192923']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['37b587da-2bc0-43f0-bbde-9f2c97192923']['InstallationDir'] = ENV["HOME"]

