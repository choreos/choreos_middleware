##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 770dcbe5-4006-4dd9-b678-cb2413e2cde7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['770dcbe5-4006-4dd9-b678-cb2413e2cde7']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['770dcbe5-4006-4dd9-b678-cb2413e2cde7']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['770dcbe5-4006-4dd9-b678-cb2413e2cde7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['770dcbe5-4006-4dd9-b678-cb2413e2cde7']['InstallationDir'] = ENV["HOME"]

