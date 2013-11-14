##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9148e047-7358-49aa-a3ec-9fffd6f36a2d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9148e047-7358-49aa-a3ec-9fffd6f36a2d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['9148e047-7358-49aa-a3ec-9fffd6f36a2d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9148e047-7358-49aa-a3ec-9fffd6f36a2d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9148e047-7358-49aa-a3ec-9fffd6f36a2d']['InstallationDir'] = ENV["HOME"]

