##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 97aaa8db-f1c2-4ba3-964b-bace8e71fd4b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['97aaa8db-f1c2-4ba3-964b-bace8e71fd4b']['InstallationDir'] = ENV["HOME"]

