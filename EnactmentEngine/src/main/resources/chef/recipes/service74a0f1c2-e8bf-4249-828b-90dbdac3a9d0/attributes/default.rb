##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 74a0f1c2-e8bf-4249-828b-90dbdac3a9d0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['74a0f1c2-e8bf-4249-828b-90dbdac3a9d0']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['74a0f1c2-e8bf-4249-828b-90dbdac3a9d0']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['74a0f1c2-e8bf-4249-828b-90dbdac3a9d0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['74a0f1c2-e8bf-4249-828b-90dbdac3a9d0']['InstallationDir'] = ENV["HOME"]

