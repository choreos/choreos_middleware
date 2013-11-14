##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7ec688c9-b875-484c-a9f9-205b3d004032 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7ec688c9-b875-484c-a9f9-205b3d004032']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['7ec688c9-b875-484c-a9f9-205b3d004032']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7ec688c9-b875-484c-a9f9-205b3d004032']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7ec688c9-b875-484c-a9f9-205b3d004032']['InstallationDir'] = ENV["HOME"]

