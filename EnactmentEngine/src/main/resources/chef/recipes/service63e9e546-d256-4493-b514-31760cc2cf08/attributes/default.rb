##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 63e9e546-d256-4493-b514-31760cc2cf08 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['63e9e546-d256-4493-b514-31760cc2cf08']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['63e9e546-d256-4493-b514-31760cc2cf08']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['63e9e546-d256-4493-b514-31760cc2cf08']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['63e9e546-d256-4493-b514-31760cc2cf08']['InstallationDir'] = ENV["HOME"]

