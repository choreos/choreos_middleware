##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f10b86ff-43bb-48fa-a3cd-4a7499cde3ea must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f10b86ff-43bb-48fa-a3cd-4a7499cde3ea']['InstallationDir'] = ENV["HOME"]

