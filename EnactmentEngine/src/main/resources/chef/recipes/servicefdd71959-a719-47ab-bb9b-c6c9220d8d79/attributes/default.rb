##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fdd71959-a719-47ab-bb9b-c6c9220d8d79 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fdd71959-a719-47ab-bb9b-c6c9220d8d79']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['fdd71959-a719-47ab-bb9b-c6c9220d8d79']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fdd71959-a719-47ab-bb9b-c6c9220d8d79']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fdd71959-a719-47ab-bb9b-c6c9220d8d79']['InstallationDir'] = ENV["HOME"]

