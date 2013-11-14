##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 36a47234-4a8f-4427-9eed-afeeae6fca83 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['36a47234-4a8f-4427-9eed-afeeae6fca83']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['36a47234-4a8f-4427-9eed-afeeae6fca83']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['36a47234-4a8f-4427-9eed-afeeae6fca83']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['36a47234-4a8f-4427-9eed-afeeae6fca83']['InstallationDir'] = ENV["HOME"]

