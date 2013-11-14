##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 58ea151f-0ad5-443c-b923-7c89c5b4afba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['58ea151f-0ad5-443c-b923-7c89c5b4afba']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['58ea151f-0ad5-443c-b923-7c89c5b4afba']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['58ea151f-0ad5-443c-b923-7c89c5b4afba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['58ea151f-0ad5-443c-b923-7c89c5b4afba']['InstallationDir'] = ENV["HOME"]

