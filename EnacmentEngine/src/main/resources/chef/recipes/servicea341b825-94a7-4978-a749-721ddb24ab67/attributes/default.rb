##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a341b825-94a7-4978-a749-721ddb24ab67 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a341b825-94a7-4978-a749-721ddb24ab67']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['a341b825-94a7-4978-a749-721ddb24ab67']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a341b825-94a7-4978-a749-721ddb24ab67']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a341b825-94a7-4978-a749-721ddb24ab67']['InstallationDir'] = ENV["HOME"]

