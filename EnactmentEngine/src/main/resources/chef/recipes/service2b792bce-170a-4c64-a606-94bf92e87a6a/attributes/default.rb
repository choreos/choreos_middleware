##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2b792bce-170a-4c64-a606-94bf92e87a6a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['WarFile'] = ""
default['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2b792bce-170a-4c64-a606-94bf92e87a6a']['InstallationDir'] = ENV["HOME"]

