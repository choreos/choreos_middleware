##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of $NAME must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['$NAME']['WarFile'] = "$WARFILE"
default['CHOReOSData']['serviceData']['$NAME']['PackageURL'] = "$PackageURL"
default['CHOReOSData']['serviceData']['$NAME']['DeploymentManagerURL'] = "$DeploymentManagerURL"
# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['$NAME']['logFile'] = "/dev/stdout"
# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['$NAME']['InstallationDir'] = ENV["HOME"]

