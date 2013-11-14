##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 14a0b696-fa81-4c14-925b-43a57e3d92c0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['14a0b696-fa81-4c14-925b-43a57e3d92c0']['InstallationDir'] = ENV["HOME"]

