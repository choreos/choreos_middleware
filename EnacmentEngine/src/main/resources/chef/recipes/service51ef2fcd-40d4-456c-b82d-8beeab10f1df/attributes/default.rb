##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 51ef2fcd-40d4-456c-b82d-8beeab10f1df must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['51ef2fcd-40d4-456c-b82d-8beeab10f1df']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['51ef2fcd-40d4-456c-b82d-8beeab10f1df']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['51ef2fcd-40d4-456c-b82d-8beeab10f1df']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['51ef2fcd-40d4-456c-b82d-8beeab10f1df']['InstallationDir'] = ENV["HOME"]

