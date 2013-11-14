##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3e0bc580-b5e4-44f2-affa-88e73444522b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3e0bc580-b5e4-44f2-affa-88e73444522b']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['3e0bc580-b5e4-44f2-affa-88e73444522b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3e0bc580-b5e4-44f2-affa-88e73444522b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3e0bc580-b5e4-44f2-affa-88e73444522b']['InstallationDir'] = ENV["HOME"]

