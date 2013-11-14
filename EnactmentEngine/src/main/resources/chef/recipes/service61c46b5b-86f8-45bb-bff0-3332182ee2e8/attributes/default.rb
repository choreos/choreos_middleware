##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 61c46b5b-86f8-45bb-bff0-3332182ee2e8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['61c46b5b-86f8-45bb-bff0-3332182ee2e8']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['61c46b5b-86f8-45bb-bff0-3332182ee2e8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['61c46b5b-86f8-45bb-bff0-3332182ee2e8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['61c46b5b-86f8-45bb-bff0-3332182ee2e8']['InstallationDir'] = ENV["HOME"]

