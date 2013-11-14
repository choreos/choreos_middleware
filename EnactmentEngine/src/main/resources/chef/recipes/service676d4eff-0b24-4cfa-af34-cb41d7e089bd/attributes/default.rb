##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 676d4eff-0b24-4cfa-af34-cb41d7e089bd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['676d4eff-0b24-4cfa-af34-cb41d7e089bd']['WarFile'] = ""
default['CHOReOSData']['serviceData']['676d4eff-0b24-4cfa-af34-cb41d7e089bd']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['676d4eff-0b24-4cfa-af34-cb41d7e089bd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['676d4eff-0b24-4cfa-af34-cb41d7e089bd']['InstallationDir'] = ENV["HOME"]

