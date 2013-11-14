##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 26608f98-df23-4f66-8470-198f8cca73ab must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['26608f98-df23-4f66-8470-198f8cca73ab']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['26608f98-df23-4f66-8470-198f8cca73ab']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['26608f98-df23-4f66-8470-198f8cca73ab']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['26608f98-df23-4f66-8470-198f8cca73ab']['InstallationDir'] = ENV["HOME"]

