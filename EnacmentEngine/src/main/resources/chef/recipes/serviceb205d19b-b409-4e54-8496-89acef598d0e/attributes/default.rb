##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b205d19b-b409-4e54-8496-89acef598d0e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b205d19b-b409-4e54-8496-89acef598d0e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['b205d19b-b409-4e54-8496-89acef598d0e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b205d19b-b409-4e54-8496-89acef598d0e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b205d19b-b409-4e54-8496-89acef598d0e']['InstallationDir'] = ENV["HOME"]

