##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c1be9c07-0c08-40cc-9a3b-8461c2a54372 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c1be9c07-0c08-40cc-9a3b-8461c2a54372']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['c1be9c07-0c08-40cc-9a3b-8461c2a54372']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c1be9c07-0c08-40cc-9a3b-8461c2a54372']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c1be9c07-0c08-40cc-9a3b-8461c2a54372']['InstallationDir'] = ENV["HOME"]

