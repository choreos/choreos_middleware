##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9ac3bfcc-5aa1-4211-8983-e9ea0b808dc7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9ac3bfcc-5aa1-4211-8983-e9ea0b808dc7']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['9ac3bfcc-5aa1-4211-8983-e9ea0b808dc7']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9ac3bfcc-5aa1-4211-8983-e9ea0b808dc7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9ac3bfcc-5aa1-4211-8983-e9ea0b808dc7']['InstallationDir'] = ENV["HOME"]

