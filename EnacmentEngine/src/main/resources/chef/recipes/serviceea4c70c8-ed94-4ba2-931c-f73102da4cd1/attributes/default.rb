##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ea4c70c8-ed94-4ba2-931c-f73102da4cd1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ea4c70c8-ed94-4ba2-931c-f73102da4cd1']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['ea4c70c8-ed94-4ba2-931c-f73102da4cd1']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ea4c70c8-ed94-4ba2-931c-f73102da4cd1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ea4c70c8-ed94-4ba2-931c-f73102da4cd1']['InstallationDir'] = ENV["HOME"]

