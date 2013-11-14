##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 04c9ac5a-b50f-4a56-bbb5-7f6626447174 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['04c9ac5a-b50f-4a56-bbb5-7f6626447174']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['04c9ac5a-b50f-4a56-bbb5-7f6626447174']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['04c9ac5a-b50f-4a56-bbb5-7f6626447174']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['04c9ac5a-b50f-4a56-bbb5-7f6626447174']['InstallationDir'] = ENV["HOME"]

