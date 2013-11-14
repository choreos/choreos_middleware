##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9842d15f-8f2c-41cd-b90d-71f32b7259c6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9842d15f-8f2c-41cd-b90d-71f32b7259c6']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['9842d15f-8f2c-41cd-b90d-71f32b7259c6']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9842d15f-8f2c-41cd-b90d-71f32b7259c6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9842d15f-8f2c-41cd-b90d-71f32b7259c6']['InstallationDir'] = ENV["HOME"]

