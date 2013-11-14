##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of eb1bd49f-93a9-4779-89d6-a8d7cab73aa7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['eb1bd49f-93a9-4779-89d6-a8d7cab73aa7']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['eb1bd49f-93a9-4779-89d6-a8d7cab73aa7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['eb1bd49f-93a9-4779-89d6-a8d7cab73aa7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['eb1bd49f-93a9-4779-89d6-a8d7cab73aa7']['InstallationDir'] = ENV["HOME"]

