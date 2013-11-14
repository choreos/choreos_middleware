##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 40805be9-bd11-45d2-9d2d-2b1b9dc48e93 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['40805be9-bd11-45d2-9d2d-2b1b9dc48e93']['WarFile'] = ""
default['CHOReOSData']['serviceData']['40805be9-bd11-45d2-9d2d-2b1b9dc48e93']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['40805be9-bd11-45d2-9d2d-2b1b9dc48e93']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['40805be9-bd11-45d2-9d2d-2b1b9dc48e93']['InstallationDir'] = ENV["HOME"]

