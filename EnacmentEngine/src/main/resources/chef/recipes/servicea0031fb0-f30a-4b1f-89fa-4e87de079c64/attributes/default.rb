##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a0031fb0-f30a-4b1f-89fa-4e87de079c64 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a0031fb0-f30a-4b1f-89fa-4e87de079c64']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a0031fb0-f30a-4b1f-89fa-4e87de079c64']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a0031fb0-f30a-4b1f-89fa-4e87de079c64']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a0031fb0-f30a-4b1f-89fa-4e87de079c64']['InstallationDir'] = ENV["HOME"]

