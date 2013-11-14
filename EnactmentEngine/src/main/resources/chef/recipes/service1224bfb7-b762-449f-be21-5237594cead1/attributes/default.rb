##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1224bfb7-b762-449f-be21-5237594cead1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1224bfb7-b762-449f-be21-5237594cead1']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['1224bfb7-b762-449f-be21-5237594cead1']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1224bfb7-b762-449f-be21-5237594cead1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1224bfb7-b762-449f-be21-5237594cead1']['InstallationDir'] = ENV["HOME"]

