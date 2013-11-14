##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 02c9d53b-8302-4546-9ee1-9e8878008ae2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['02c9d53b-8302-4546-9ee1-9e8878008ae2']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['02c9d53b-8302-4546-9ee1-9e8878008ae2']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['02c9d53b-8302-4546-9ee1-9e8878008ae2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['02c9d53b-8302-4546-9ee1-9e8878008ae2']['InstallationDir'] = ENV["HOME"]

