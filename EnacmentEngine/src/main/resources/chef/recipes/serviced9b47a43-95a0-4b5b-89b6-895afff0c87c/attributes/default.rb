##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d9b47a43-95a0-4b5b-89b6-895afff0c87c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d9b47a43-95a0-4b5b-89b6-895afff0c87c']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['d9b47a43-95a0-4b5b-89b6-895afff0c87c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d9b47a43-95a0-4b5b-89b6-895afff0c87c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d9b47a43-95a0-4b5b-89b6-895afff0c87c']['InstallationDir'] = ENV["HOME"]

