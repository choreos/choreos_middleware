##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 912440a4-4eda-4451-85db-03d86d63984d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['912440a4-4eda-4451-85db-03d86d63984d']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['912440a4-4eda-4451-85db-03d86d63984d']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['912440a4-4eda-4451-85db-03d86d63984d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['912440a4-4eda-4451-85db-03d86d63984d']['InstallationDir'] = ENV["HOME"]

