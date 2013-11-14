##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a6844fd9-7fcd-43ec-a28e-bcc973c77091 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a6844fd9-7fcd-43ec-a28e-bcc973c77091']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['a6844fd9-7fcd-43ec-a28e-bcc973c77091']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a6844fd9-7fcd-43ec-a28e-bcc973c77091']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a6844fd9-7fcd-43ec-a28e-bcc973c77091']['InstallationDir'] = ENV["HOME"]

