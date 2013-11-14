##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d6ec0cca-0887-4f9b-8dda-53c34b7c0f88 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d6ec0cca-0887-4f9b-8dda-53c34b7c0f88']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['d6ec0cca-0887-4f9b-8dda-53c34b7c0f88']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d6ec0cca-0887-4f9b-8dda-53c34b7c0f88']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d6ec0cca-0887-4f9b-8dda-53c34b7c0f88']['InstallationDir'] = ENV["HOME"]

