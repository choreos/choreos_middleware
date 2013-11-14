##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5270a2cf-30c7-48df-9629-81f25829d37e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5270a2cf-30c7-48df-9629-81f25829d37e']['InstallationDir'] = ENV["HOME"]

