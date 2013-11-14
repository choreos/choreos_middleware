##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of dd2e66c6-1987-4918-9c2c-f0de11107c3d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['dd2e66c6-1987-4918-9c2c-f0de11107c3d']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['dd2e66c6-1987-4918-9c2c-f0de11107c3d']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['dd2e66c6-1987-4918-9c2c-f0de11107c3d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['dd2e66c6-1987-4918-9c2c-f0de11107c3d']['InstallationDir'] = ENV["HOME"]

