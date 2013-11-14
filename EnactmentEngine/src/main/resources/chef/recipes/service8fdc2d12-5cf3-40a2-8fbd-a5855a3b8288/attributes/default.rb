##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8fdc2d12-5cf3-40a2-8fbd-a5855a3b8288']['InstallationDir'] = ENV["HOME"]

