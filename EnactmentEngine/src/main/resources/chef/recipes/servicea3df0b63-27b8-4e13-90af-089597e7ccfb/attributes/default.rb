##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a3df0b63-27b8-4e13-90af-089597e7ccfb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a3df0b63-27b8-4e13-90af-089597e7ccfb']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a3df0b63-27b8-4e13-90af-089597e7ccfb']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a3df0b63-27b8-4e13-90af-089597e7ccfb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a3df0b63-27b8-4e13-90af-089597e7ccfb']['InstallationDir'] = ENV["HOME"]

