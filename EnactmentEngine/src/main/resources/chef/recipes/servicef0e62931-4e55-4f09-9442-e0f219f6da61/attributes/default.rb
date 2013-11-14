##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f0e62931-4e55-4f09-9442-e0f219f6da61 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f0e62931-4e55-4f09-9442-e0f219f6da61']['InstallationDir'] = ENV["HOME"]

