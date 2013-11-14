##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7bd8891e-d613-47d7-968b-550b3c4ac359 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7bd8891e-d613-47d7-968b-550b3c4ac359']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['7bd8891e-d613-47d7-968b-550b3c4ac359']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7bd8891e-d613-47d7-968b-550b3c4ac359']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7bd8891e-d613-47d7-968b-550b3c4ac359']['InstallationDir'] = ENV["HOME"]

