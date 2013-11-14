##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 02354988-5f1e-4b9b-a4b6-d6cda6de6ee3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['02354988-5f1e-4b9b-a4b6-d6cda6de6ee3']['InstallationDir'] = ENV["HOME"]

