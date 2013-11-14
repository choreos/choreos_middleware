##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e0a19a83-dae1-45bb-8db1-c4fd8ae562ba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e0a19a83-dae1-45bb-8db1-c4fd8ae562ba']['InstallationDir'] = ENV["HOME"]

