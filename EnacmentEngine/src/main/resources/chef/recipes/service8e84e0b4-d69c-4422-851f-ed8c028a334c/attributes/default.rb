##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8e84e0b4-d69c-4422-851f-ed8c028a334c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8e84e0b4-d69c-4422-851f-ed8c028a334c']['InstallationDir'] = ENV["HOME"]

