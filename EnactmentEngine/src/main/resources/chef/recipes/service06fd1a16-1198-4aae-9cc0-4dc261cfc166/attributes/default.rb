##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 06fd1a16-1198-4aae-9cc0-4dc261cfc166 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['06fd1a16-1198-4aae-9cc0-4dc261cfc166']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['06fd1a16-1198-4aae-9cc0-4dc261cfc166']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['06fd1a16-1198-4aae-9cc0-4dc261cfc166']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['06fd1a16-1198-4aae-9cc0-4dc261cfc166']['InstallationDir'] = ENV["HOME"]

