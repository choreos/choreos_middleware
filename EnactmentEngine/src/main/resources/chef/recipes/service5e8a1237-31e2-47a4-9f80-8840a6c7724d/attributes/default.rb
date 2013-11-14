##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5e8a1237-31e2-47a4-9f80-8840a6c7724d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5e8a1237-31e2-47a4-9f80-8840a6c7724d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['5e8a1237-31e2-47a4-9f80-8840a6c7724d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5e8a1237-31e2-47a4-9f80-8840a6c7724d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5e8a1237-31e2-47a4-9f80-8840a6c7724d']['InstallationDir'] = ENV["HOME"]

