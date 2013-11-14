##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7b16bc70-156c-4e34-b507-4efffe68556e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7b16bc70-156c-4e34-b507-4efffe68556e']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['7b16bc70-156c-4e34-b507-4efffe68556e']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7b16bc70-156c-4e34-b507-4efffe68556e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7b16bc70-156c-4e34-b507-4efffe68556e']['InstallationDir'] = ENV["HOME"]

