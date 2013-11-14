##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5602164f-6ad6-4d8c-970d-3034e97b13a7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5602164f-6ad6-4d8c-970d-3034e97b13a7']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['5602164f-6ad6-4d8c-970d-3034e97b13a7']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5602164f-6ad6-4d8c-970d-3034e97b13a7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5602164f-6ad6-4d8c-970d-3034e97b13a7']['InstallationDir'] = ENV["HOME"]

