##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 34742ae4-9f73-4907-bbba-0f6f56c13ba3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['34742ae4-9f73-4907-bbba-0f6f56c13ba3']['InstallationDir'] = ENV["HOME"]

