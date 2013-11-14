##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6d108dec-2e8d-4b50-a8e4-cd3d1fe27bde']['InstallationDir'] = ENV["HOME"]

