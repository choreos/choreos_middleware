##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 655ab0b4-7476-46db-b38c-fdffa476cc5d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['655ab0b4-7476-46db-b38c-fdffa476cc5d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['655ab0b4-7476-46db-b38c-fdffa476cc5d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['655ab0b4-7476-46db-b38c-fdffa476cc5d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['655ab0b4-7476-46db-b38c-fdffa476cc5d']['InstallationDir'] = ENV["HOME"]

