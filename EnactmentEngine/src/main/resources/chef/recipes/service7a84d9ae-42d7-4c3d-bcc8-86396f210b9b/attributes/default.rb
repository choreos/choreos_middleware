##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7a84d9ae-42d7-4c3d-bcc8-86396f210b9b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7a84d9ae-42d7-4c3d-bcc8-86396f210b9b']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['7a84d9ae-42d7-4c3d-bcc8-86396f210b9b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7a84d9ae-42d7-4c3d-bcc8-86396f210b9b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7a84d9ae-42d7-4c3d-bcc8-86396f210b9b']['InstallationDir'] = ENV["HOME"]

