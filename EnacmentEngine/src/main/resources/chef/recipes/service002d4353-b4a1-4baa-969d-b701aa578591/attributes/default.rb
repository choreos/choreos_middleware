##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 002d4353-b4a1-4baa-969d-b701aa578591 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['002d4353-b4a1-4baa-969d-b701aa578591']['WarFile'] = ""
default['CHOReOSData']['serviceData']['002d4353-b4a1-4baa-969d-b701aa578591']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['002d4353-b4a1-4baa-969d-b701aa578591']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['002d4353-b4a1-4baa-969d-b701aa578591']['InstallationDir'] = ENV["HOME"]

