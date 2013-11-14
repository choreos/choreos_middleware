##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6d0517c7-1613-4a05-be87-ba4aabdad3db must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6d0517c7-1613-4a05-be87-ba4aabdad3db']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['6d0517c7-1613-4a05-be87-ba4aabdad3db']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6d0517c7-1613-4a05-be87-ba4aabdad3db']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6d0517c7-1613-4a05-be87-ba4aabdad3db']['InstallationDir'] = ENV["HOME"]

