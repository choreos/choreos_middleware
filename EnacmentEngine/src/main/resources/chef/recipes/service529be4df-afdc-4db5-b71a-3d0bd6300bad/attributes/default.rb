##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 529be4df-afdc-4db5-b71a-3d0bd6300bad must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['529be4df-afdc-4db5-b71a-3d0bd6300bad']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['529be4df-afdc-4db5-b71a-3d0bd6300bad']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['529be4df-afdc-4db5-b71a-3d0bd6300bad']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['529be4df-afdc-4db5-b71a-3d0bd6300bad']['InstallationDir'] = ENV["HOME"]

