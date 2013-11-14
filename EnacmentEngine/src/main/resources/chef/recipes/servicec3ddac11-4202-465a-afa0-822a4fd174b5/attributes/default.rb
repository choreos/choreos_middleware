##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c3ddac11-4202-465a-afa0-822a4fd174b5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c3ddac11-4202-465a-afa0-822a4fd174b5']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['c3ddac11-4202-465a-afa0-822a4fd174b5']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c3ddac11-4202-465a-afa0-822a4fd174b5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c3ddac11-4202-465a-afa0-822a4fd174b5']['InstallationDir'] = ENV["HOME"]

