##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7ab73ca3-e7d7-4245-b7e9-d5bff01d9883 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7ab73ca3-e7d7-4245-b7e9-d5bff01d9883']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7ab73ca3-e7d7-4245-b7e9-d5bff01d9883']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7ab73ca3-e7d7-4245-b7e9-d5bff01d9883']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7ab73ca3-e7d7-4245-b7e9-d5bff01d9883']['InstallationDir'] = ENV["HOME"]

