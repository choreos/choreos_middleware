##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d46db643-1ada-44ff-a4af-28de1a76aba2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d46db643-1ada-44ff-a4af-28de1a76aba2']['InstallationDir'] = ENV["HOME"]

