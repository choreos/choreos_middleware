##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4a1205ab-342c-4c4f-8eed-017c9106b69a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4a1205ab-342c-4c4f-8eed-017c9106b69a']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['4a1205ab-342c-4c4f-8eed-017c9106b69a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4a1205ab-342c-4c4f-8eed-017c9106b69a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4a1205ab-342c-4c4f-8eed-017c9106b69a']['InstallationDir'] = ENV["HOME"]
