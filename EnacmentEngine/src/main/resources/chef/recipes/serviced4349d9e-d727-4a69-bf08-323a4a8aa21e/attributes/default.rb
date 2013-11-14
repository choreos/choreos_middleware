##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d4349d9e-d727-4a69-bf08-323a4a8aa21e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d4349d9e-d727-4a69-bf08-323a4a8aa21e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['d4349d9e-d727-4a69-bf08-323a4a8aa21e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d4349d9e-d727-4a69-bf08-323a4a8aa21e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d4349d9e-d727-4a69-bf08-323a4a8aa21e']['InstallationDir'] = ENV["HOME"]

