##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a26458f4-d8df-483b-8375-63a2030ee914 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a26458f4-d8df-483b-8375-63a2030ee914']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['a26458f4-d8df-483b-8375-63a2030ee914']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a26458f4-d8df-483b-8375-63a2030ee914']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a26458f4-d8df-483b-8375-63a2030ee914']['InstallationDir'] = ENV["HOME"]

