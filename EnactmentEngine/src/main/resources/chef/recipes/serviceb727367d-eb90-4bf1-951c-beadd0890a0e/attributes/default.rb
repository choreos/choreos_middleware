##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b727367d-eb90-4bf1-951c-beadd0890a0e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b727367d-eb90-4bf1-951c-beadd0890a0e']['InstallationDir'] = ENV["HOME"]

