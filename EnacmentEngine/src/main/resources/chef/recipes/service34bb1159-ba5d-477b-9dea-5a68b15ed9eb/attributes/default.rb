##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 34bb1159-ba5d-477b-9dea-5a68b15ed9eb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['34bb1159-ba5d-477b-9dea-5a68b15ed9eb']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['34bb1159-ba5d-477b-9dea-5a68b15ed9eb']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['34bb1159-ba5d-477b-9dea-5a68b15ed9eb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['34bb1159-ba5d-477b-9dea-5a68b15ed9eb']['InstallationDir'] = ENV["HOME"]

