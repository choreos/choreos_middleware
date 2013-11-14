##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a3800fa6-1bb1-49dc-824b-f59d7624edaf must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a3800fa6-1bb1-49dc-824b-f59d7624edaf']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a3800fa6-1bb1-49dc-824b-f59d7624edaf']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a3800fa6-1bb1-49dc-824b-f59d7624edaf']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a3800fa6-1bb1-49dc-824b-f59d7624edaf']['InstallationDir'] = ENV["HOME"]

