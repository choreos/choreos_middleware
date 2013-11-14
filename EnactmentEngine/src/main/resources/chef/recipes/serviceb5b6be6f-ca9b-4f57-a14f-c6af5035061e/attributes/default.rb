##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b5b6be6f-ca9b-4f57-a14f-c6af5035061e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b5b6be6f-ca9b-4f57-a14f-c6af5035061e']['InstallationDir'] = ENV["HOME"]

