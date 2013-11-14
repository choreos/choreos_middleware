##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4ddf5983-c1d7-4d6f-8574-13a06c3389be must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4ddf5983-c1d7-4d6f-8574-13a06c3389be']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['4ddf5983-c1d7-4d6f-8574-13a06c3389be']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4ddf5983-c1d7-4d6f-8574-13a06c3389be']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4ddf5983-c1d7-4d6f-8574-13a06c3389be']['InstallationDir'] = ENV["HOME"]

