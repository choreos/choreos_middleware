##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a7afd39d-3c84-4899-9f46-3fdee245ff11 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a7afd39d-3c84-4899-9f46-3fdee245ff11']['InstallationDir'] = ENV["HOME"]

