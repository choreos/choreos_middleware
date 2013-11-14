##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8ef7f8b5-2493-48ec-ae38-fa266e05762b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8ef7f8b5-2493-48ec-ae38-fa266e05762b']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['8ef7f8b5-2493-48ec-ae38-fa266e05762b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8ef7f8b5-2493-48ec-ae38-fa266e05762b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8ef7f8b5-2493-48ec-ae38-fa266e05762b']['InstallationDir'] = ENV["HOME"]

