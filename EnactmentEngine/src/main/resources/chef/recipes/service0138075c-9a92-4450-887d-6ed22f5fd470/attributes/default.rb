##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0138075c-9a92-4450-887d-6ed22f5fd470 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0138075c-9a92-4450-887d-6ed22f5fd470']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['0138075c-9a92-4450-887d-6ed22f5fd470']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0138075c-9a92-4450-887d-6ed22f5fd470']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0138075c-9a92-4450-887d-6ed22f5fd470']['InstallationDir'] = ENV["HOME"]

