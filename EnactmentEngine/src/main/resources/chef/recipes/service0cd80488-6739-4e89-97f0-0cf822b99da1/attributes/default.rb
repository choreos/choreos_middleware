##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0cd80488-6739-4e89-97f0-0cf822b99da1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0cd80488-6739-4e89-97f0-0cf822b99da1']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['0cd80488-6739-4e89-97f0-0cf822b99da1']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0cd80488-6739-4e89-97f0-0cf822b99da1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0cd80488-6739-4e89-97f0-0cf822b99da1']['InstallationDir'] = ENV["HOME"]

