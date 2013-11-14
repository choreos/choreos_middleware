##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 64d98535-bc16-45ab-92c8-171759306568 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['64d98535-bc16-45ab-92c8-171759306568']['WarFile'] = ""
default['CHOReOSData']['serviceData']['64d98535-bc16-45ab-92c8-171759306568']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['64d98535-bc16-45ab-92c8-171759306568']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['64d98535-bc16-45ab-92c8-171759306568']['InstallationDir'] = ENV["HOME"]

