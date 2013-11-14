##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 174d4b77-aaf1-4019-af40-da3dcd18c00c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['174d4b77-aaf1-4019-af40-da3dcd18c00c']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['174d4b77-aaf1-4019-af40-da3dcd18c00c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['174d4b77-aaf1-4019-af40-da3dcd18c00c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['174d4b77-aaf1-4019-af40-da3dcd18c00c']['InstallationDir'] = ENV["HOME"]

