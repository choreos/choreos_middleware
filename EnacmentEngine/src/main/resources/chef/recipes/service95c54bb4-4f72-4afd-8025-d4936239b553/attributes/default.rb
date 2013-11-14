##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 95c54bb4-4f72-4afd-8025-d4936239b553 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['95c54bb4-4f72-4afd-8025-d4936239b553']['InstallationDir'] = ENV["HOME"]

