##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9848cfb1-56f8-4b52-9f92-7dbf7bec2114 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9848cfb1-56f8-4b52-9f92-7dbf7bec2114']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['9848cfb1-56f8-4b52-9f92-7dbf7bec2114']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9848cfb1-56f8-4b52-9f92-7dbf7bec2114']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9848cfb1-56f8-4b52-9f92-7dbf7bec2114']['InstallationDir'] = ENV["HOME"]

