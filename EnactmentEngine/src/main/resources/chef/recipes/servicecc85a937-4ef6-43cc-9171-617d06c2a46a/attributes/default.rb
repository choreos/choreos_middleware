##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of cc85a937-4ef6-43cc-9171-617d06c2a46a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['cc85a937-4ef6-43cc-9171-617d06c2a46a']['InstallationDir'] = ENV["HOME"]

