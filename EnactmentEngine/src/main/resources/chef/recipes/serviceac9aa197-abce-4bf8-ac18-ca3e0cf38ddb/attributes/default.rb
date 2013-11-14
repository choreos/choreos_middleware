##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ac9aa197-abce-4bf8-ac18-ca3e0cf38ddb']['InstallationDir'] = ENV["HOME"]

