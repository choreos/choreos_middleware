##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6d4de316-e651-4b37-9b15-520f74608579 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6d4de316-e651-4b37-9b15-520f74608579']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['6d4de316-e651-4b37-9b15-520f74608579']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6d4de316-e651-4b37-9b15-520f74608579']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6d4de316-e651-4b37-9b15-520f74608579']['InstallationDir'] = ENV["HOME"]

