##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ca93a6af-b055-484a-b3b0-d532e7e0cb75 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ca93a6af-b055-484a-b3b0-d532e7e0cb75']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['ca93a6af-b055-484a-b3b0-d532e7e0cb75']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ca93a6af-b055-484a-b3b0-d532e7e0cb75']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ca93a6af-b055-484a-b3b0-d532e7e0cb75']['InstallationDir'] = ENV["HOME"]

