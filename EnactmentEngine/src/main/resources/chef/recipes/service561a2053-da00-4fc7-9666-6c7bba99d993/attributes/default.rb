##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 561a2053-da00-4fc7-9666-6c7bba99d993 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['561a2053-da00-4fc7-9666-6c7bba99d993']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['561a2053-da00-4fc7-9666-6c7bba99d993']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['561a2053-da00-4fc7-9666-6c7bba99d993']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['561a2053-da00-4fc7-9666-6c7bba99d993']['InstallationDir'] = ENV["HOME"]

