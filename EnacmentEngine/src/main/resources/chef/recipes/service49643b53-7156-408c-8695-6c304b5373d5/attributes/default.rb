##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 49643b53-7156-408c-8695-6c304b5373d5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['49643b53-7156-408c-8695-6c304b5373d5']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['49643b53-7156-408c-8695-6c304b5373d5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['49643b53-7156-408c-8695-6c304b5373d5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['49643b53-7156-408c-8695-6c304b5373d5']['InstallationDir'] = ENV["HOME"]

