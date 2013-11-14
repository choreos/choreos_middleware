##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of df2b366b-270f-46d8-b867-b8853ea14407 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['df2b366b-270f-46d8-b867-b8853ea14407']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['df2b366b-270f-46d8-b867-b8853ea14407']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['df2b366b-270f-46d8-b867-b8853ea14407']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['df2b366b-270f-46d8-b867-b8853ea14407']['InstallationDir'] = ENV["HOME"]

