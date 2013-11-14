##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 68e6afa0-382e-412a-aa7d-038ffc740d2f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['68e6afa0-382e-412a-aa7d-038ffc740d2f']['InstallationDir'] = ENV["HOME"]

