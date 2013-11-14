##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 76db9ee7-0810-4091-9154-c27763debca3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['76db9ee7-0810-4091-9154-c27763debca3']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['76db9ee7-0810-4091-9154-c27763debca3']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['76db9ee7-0810-4091-9154-c27763debca3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['76db9ee7-0810-4091-9154-c27763debca3']['InstallationDir'] = ENV["HOME"]

