##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d95aa5cc-feb1-4481-b804-79a944bfb8b5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d95aa5cc-feb1-4481-b804-79a944bfb8b5']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['d95aa5cc-feb1-4481-b804-79a944bfb8b5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d95aa5cc-feb1-4481-b804-79a944bfb8b5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d95aa5cc-feb1-4481-b804-79a944bfb8b5']['InstallationDir'] = ENV["HOME"]

