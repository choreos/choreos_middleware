##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 48003abc-f49f-4ebd-a268-11415b42c1d4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['48003abc-f49f-4ebd-a268-11415b42c1d4']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['48003abc-f49f-4ebd-a268-11415b42c1d4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['48003abc-f49f-4ebd-a268-11415b42c1d4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['48003abc-f49f-4ebd-a268-11415b42c1d4']['InstallationDir'] = ENV["HOME"]

