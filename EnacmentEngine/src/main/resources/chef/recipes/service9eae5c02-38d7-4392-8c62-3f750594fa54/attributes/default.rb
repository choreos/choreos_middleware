##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9eae5c02-38d7-4392-8c62-3f750594fa54 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9eae5c02-38d7-4392-8c62-3f750594fa54']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['9eae5c02-38d7-4392-8c62-3f750594fa54']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9eae5c02-38d7-4392-8c62-3f750594fa54']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9eae5c02-38d7-4392-8c62-3f750594fa54']['InstallationDir'] = ENV["HOME"]

