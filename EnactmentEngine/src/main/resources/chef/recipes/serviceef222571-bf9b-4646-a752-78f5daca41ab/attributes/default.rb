##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ef222571-bf9b-4646-a752-78f5daca41ab must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ef222571-bf9b-4646-a752-78f5daca41ab']['InstallationDir'] = ENV["HOME"]

