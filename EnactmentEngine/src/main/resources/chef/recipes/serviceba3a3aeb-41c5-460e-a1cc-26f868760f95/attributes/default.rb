##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ba3a3aeb-41c5-460e-a1cc-26f868760f95 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ba3a3aeb-41c5-460e-a1cc-26f868760f95']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['ba3a3aeb-41c5-460e-a1cc-26f868760f95']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ba3a3aeb-41c5-460e-a1cc-26f868760f95']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ba3a3aeb-41c5-460e-a1cc-26f868760f95']['InstallationDir'] = ENV["HOME"]

