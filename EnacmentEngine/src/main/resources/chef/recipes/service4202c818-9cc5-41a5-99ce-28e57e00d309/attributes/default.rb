##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4202c818-9cc5-41a5-99ce-28e57e00d309 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4202c818-9cc5-41a5-99ce-28e57e00d309']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['4202c818-9cc5-41a5-99ce-28e57e00d309']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4202c818-9cc5-41a5-99ce-28e57e00d309']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4202c818-9cc5-41a5-99ce-28e57e00d309']['InstallationDir'] = ENV["HOME"]

