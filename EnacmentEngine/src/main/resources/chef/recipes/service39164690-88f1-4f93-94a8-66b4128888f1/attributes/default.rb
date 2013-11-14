##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 39164690-88f1-4f93-94a8-66b4128888f1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['39164690-88f1-4f93-94a8-66b4128888f1']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['39164690-88f1-4f93-94a8-66b4128888f1']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['39164690-88f1-4f93-94a8-66b4128888f1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['39164690-88f1-4f93-94a8-66b4128888f1']['InstallationDir'] = ENV["HOME"]

