##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 45fcdbfe-3e29-4bac-81f7-af9968790f4e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['45fcdbfe-3e29-4bac-81f7-af9968790f4e']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['45fcdbfe-3e29-4bac-81f7-af9968790f4e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['45fcdbfe-3e29-4bac-81f7-af9968790f4e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['45fcdbfe-3e29-4bac-81f7-af9968790f4e']['InstallationDir'] = ENV["HOME"]

