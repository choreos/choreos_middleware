##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e3a13424-be57-4237-a007-f9c231429638 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e3a13424-be57-4237-a007-f9c231429638']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['e3a13424-be57-4237-a007-f9c231429638']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e3a13424-be57-4237-a007-f9c231429638']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e3a13424-be57-4237-a007-f9c231429638']['InstallationDir'] = ENV["HOME"]

