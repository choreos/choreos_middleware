##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 97b3d865-16d4-4e31-8849-f78abc0d915d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['97b3d865-16d4-4e31-8849-f78abc0d915d']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['97b3d865-16d4-4e31-8849-f78abc0d915d']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['97b3d865-16d4-4e31-8849-f78abc0d915d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['97b3d865-16d4-4e31-8849-f78abc0d915d']['InstallationDir'] = ENV["HOME"]

