##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b']['InstallationDir'] = ENV["HOME"]

