##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f52aa599-5b2a-4ed8-98cc-f78172bfa804 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f52aa599-5b2a-4ed8-98cc-f78172bfa804']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['f52aa599-5b2a-4ed8-98cc-f78172bfa804']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f52aa599-5b2a-4ed8-98cc-f78172bfa804']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f52aa599-5b2a-4ed8-98cc-f78172bfa804']['InstallationDir'] = ENV["HOME"]

