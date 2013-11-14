##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d9a6689c-78ea-4044-b150-2f281990e411 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d9a6689c-78ea-4044-b150-2f281990e411']['InstallationDir'] = ENV["HOME"]

