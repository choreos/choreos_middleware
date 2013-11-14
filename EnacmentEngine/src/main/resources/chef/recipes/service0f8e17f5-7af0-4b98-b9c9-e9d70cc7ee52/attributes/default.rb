##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0f8e17f5-7af0-4b98-b9c9-e9d70cc7ee52']['InstallationDir'] = ENV["HOME"]

