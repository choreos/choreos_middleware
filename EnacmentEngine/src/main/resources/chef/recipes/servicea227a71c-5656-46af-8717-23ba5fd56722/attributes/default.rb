##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a227a71c-5656-46af-8717-23ba5fd56722 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a227a71c-5656-46af-8717-23ba5fd56722']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a227a71c-5656-46af-8717-23ba5fd56722']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a227a71c-5656-46af-8717-23ba5fd56722']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a227a71c-5656-46af-8717-23ba5fd56722']['InstallationDir'] = ENV["HOME"]

