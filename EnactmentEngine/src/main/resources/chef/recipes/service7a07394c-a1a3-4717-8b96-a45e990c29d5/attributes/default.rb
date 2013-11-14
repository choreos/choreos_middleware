##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7a07394c-a1a3-4717-8b96-a45e990c29d5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7a07394c-a1a3-4717-8b96-a45e990c29d5']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7a07394c-a1a3-4717-8b96-a45e990c29d5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7a07394c-a1a3-4717-8b96-a45e990c29d5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7a07394c-a1a3-4717-8b96-a45e990c29d5']['InstallationDir'] = ENV["HOME"]

