##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of bff19f98-1836-4f71-9445-8f7322b02702 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['bff19f98-1836-4f71-9445-8f7322b02702']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['bff19f98-1836-4f71-9445-8f7322b02702']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['bff19f98-1836-4f71-9445-8f7322b02702']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['bff19f98-1836-4f71-9445-8f7322b02702']['InstallationDir'] = ENV["HOME"]

