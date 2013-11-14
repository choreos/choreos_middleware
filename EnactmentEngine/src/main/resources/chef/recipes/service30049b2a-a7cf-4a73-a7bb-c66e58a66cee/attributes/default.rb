##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 30049b2a-a7cf-4a73-a7bb-c66e58a66cee must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['WarFile'] = ""
default['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['30049b2a-a7cf-4a73-a7bb-c66e58a66cee']['InstallationDir'] = ENV["HOME"]

