##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3e080980-285b-4e00-ba41-9671bf30dca3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3e080980-285b-4e00-ba41-9671bf30dca3']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['3e080980-285b-4e00-ba41-9671bf30dca3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3e080980-285b-4e00-ba41-9671bf30dca3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3e080980-285b-4e00-ba41-9671bf30dca3']['InstallationDir'] = ENV["HOME"]

