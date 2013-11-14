##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a35054ce-afeb-48c0-a4e7-4f3d79ffb95c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a35054ce-afeb-48c0-a4e7-4f3d79ffb95c']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a35054ce-afeb-48c0-a4e7-4f3d79ffb95c']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a35054ce-afeb-48c0-a4e7-4f3d79ffb95c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a35054ce-afeb-48c0-a4e7-4f3d79ffb95c']['InstallationDir'] = ENV["HOME"]

