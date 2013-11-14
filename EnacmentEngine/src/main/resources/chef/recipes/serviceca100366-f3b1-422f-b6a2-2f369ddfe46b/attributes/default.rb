##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ca100366-f3b1-422f-b6a2-2f369ddfe46b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ca100366-f3b1-422f-b6a2-2f369ddfe46b']['InstallationDir'] = ENV["HOME"]

