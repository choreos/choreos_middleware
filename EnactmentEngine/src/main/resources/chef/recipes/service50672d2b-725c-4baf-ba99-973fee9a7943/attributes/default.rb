##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 50672d2b-725c-4baf-ba99-973fee9a7943 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['WarFile'] = ""
default['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['50672d2b-725c-4baf-ba99-973fee9a7943']['InstallationDir'] = ENV["HOME"]

