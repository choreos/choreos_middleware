##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of cfe64896-36b4-4b64-a072-9ed76d6cfcf2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['cfe64896-36b4-4b64-a072-9ed76d6cfcf2']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['cfe64896-36b4-4b64-a072-9ed76d6cfcf2']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['cfe64896-36b4-4b64-a072-9ed76d6cfcf2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['cfe64896-36b4-4b64-a072-9ed76d6cfcf2']['InstallationDir'] = ENV["HOME"]

