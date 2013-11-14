##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3da4f7b7-eb1c-4bda-9773-fe84847f0046 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3da4f7b7-eb1c-4bda-9773-fe84847f0046']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['3da4f7b7-eb1c-4bda-9773-fe84847f0046']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3da4f7b7-eb1c-4bda-9773-fe84847f0046']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3da4f7b7-eb1c-4bda-9773-fe84847f0046']['InstallationDir'] = ENV["HOME"]

