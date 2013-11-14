##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2122d5d0-c730-4c32-a2e8-89316b138d1d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2122d5d0-c730-4c32-a2e8-89316b138d1d']['WarFile'] = ""
default['CHOReOSData']['serviceData']['2122d5d0-c730-4c32-a2e8-89316b138d1d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2122d5d0-c730-4c32-a2e8-89316b138d1d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2122d5d0-c730-4c32-a2e8-89316b138d1d']['InstallationDir'] = ENV["HOME"]

