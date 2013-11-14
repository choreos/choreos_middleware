##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ad890e55-0946-4a82-98b7-d1167b0a9ea6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ad890e55-0946-4a82-98b7-d1167b0a9ea6']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['ad890e55-0946-4a82-98b7-d1167b0a9ea6']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ad890e55-0946-4a82-98b7-d1167b0a9ea6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ad890e55-0946-4a82-98b7-d1167b0a9ea6']['InstallationDir'] = ENV["HOME"]

