##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a58195b0-960e-462b-8f6b-48442c3ec0df must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a58195b0-960e-462b-8f6b-48442c3ec0df']['InstallationDir'] = ENV["HOME"]

