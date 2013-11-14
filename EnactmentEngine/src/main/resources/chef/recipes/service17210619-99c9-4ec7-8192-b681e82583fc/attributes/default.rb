##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 17210619-99c9-4ec7-8192-b681e82583fc must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['17210619-99c9-4ec7-8192-b681e82583fc']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['17210619-99c9-4ec7-8192-b681e82583fc']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['17210619-99c9-4ec7-8192-b681e82583fc']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['17210619-99c9-4ec7-8192-b681e82583fc']['InstallationDir'] = ENV["HOME"]

