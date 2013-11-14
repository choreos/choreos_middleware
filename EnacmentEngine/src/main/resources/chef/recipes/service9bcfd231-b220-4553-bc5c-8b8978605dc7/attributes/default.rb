##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9bcfd231-b220-4553-bc5c-8b8978605dc7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9bcfd231-b220-4553-bc5c-8b8978605dc7']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['9bcfd231-b220-4553-bc5c-8b8978605dc7']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9bcfd231-b220-4553-bc5c-8b8978605dc7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9bcfd231-b220-4553-bc5c-8b8978605dc7']['InstallationDir'] = ENV["HOME"]

