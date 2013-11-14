##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0f87dbb8-e09f-4d5d-977f-91f03ebf8150 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0f87dbb8-e09f-4d5d-977f-91f03ebf8150']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['0f87dbb8-e09f-4d5d-977f-91f03ebf8150']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0f87dbb8-e09f-4d5d-977f-91f03ebf8150']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0f87dbb8-e09f-4d5d-977f-91f03ebf8150']['InstallationDir'] = ENV["HOME"]

