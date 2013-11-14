##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 538d0fac-e098-471a-8b91-b2c7a1d39280 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['538d0fac-e098-471a-8b91-b2c7a1d39280']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['538d0fac-e098-471a-8b91-b2c7a1d39280']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['538d0fac-e098-471a-8b91-b2c7a1d39280']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['538d0fac-e098-471a-8b91-b2c7a1d39280']['InstallationDir'] = ENV["HOME"]

