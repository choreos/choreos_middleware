##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b2a769e4-1304-4e8e-a4dc-ca9a607a4d75 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b2a769e4-1304-4e8e-a4dc-ca9a607a4d75']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['b2a769e4-1304-4e8e-a4dc-ca9a607a4d75']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b2a769e4-1304-4e8e-a4dc-ca9a607a4d75']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b2a769e4-1304-4e8e-a4dc-ca9a607a4d75']['InstallationDir'] = ENV["HOME"]

