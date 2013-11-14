##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c45fa133-41bd-46c3-bb8f-38f866812482 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c45fa133-41bd-46c3-bb8f-38f866812482']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['c45fa133-41bd-46c3-bb8f-38f866812482']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c45fa133-41bd-46c3-bb8f-38f866812482']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c45fa133-41bd-46c3-bb8f-38f866812482']['InstallationDir'] = ENV["HOME"]

