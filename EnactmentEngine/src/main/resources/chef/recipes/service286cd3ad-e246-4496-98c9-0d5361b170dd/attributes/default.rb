##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 286cd3ad-e246-4496-98c9-0d5361b170dd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['286cd3ad-e246-4496-98c9-0d5361b170dd']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['286cd3ad-e246-4496-98c9-0d5361b170dd']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['286cd3ad-e246-4496-98c9-0d5361b170dd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['286cd3ad-e246-4496-98c9-0d5361b170dd']['InstallationDir'] = ENV["HOME"]

