##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of acaa502d-8a89-46c2-838a-fee975e03cec must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['acaa502d-8a89-46c2-838a-fee975e03cec']['InstallationDir'] = ENV["HOME"]

