##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of bb30a053-3d8c-4a36-be7a-c521674ebf0b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['bb30a053-3d8c-4a36-be7a-c521674ebf0b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['bb30a053-3d8c-4a36-be7a-c521674ebf0b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['bb30a053-3d8c-4a36-be7a-c521674ebf0b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['bb30a053-3d8c-4a36-be7a-c521674ebf0b']['InstallationDir'] = ENV["HOME"]

