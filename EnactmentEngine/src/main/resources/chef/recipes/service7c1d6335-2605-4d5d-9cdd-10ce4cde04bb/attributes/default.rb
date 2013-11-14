##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7c1d6335-2605-4d5d-9cdd-10ce4cde04bb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7c1d6335-2605-4d5d-9cdd-10ce4cde04bb']['WarFile'] = ""
default['CHOReOSData']['serviceData']['7c1d6335-2605-4d5d-9cdd-10ce4cde04bb']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7c1d6335-2605-4d5d-9cdd-10ce4cde04bb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7c1d6335-2605-4d5d-9cdd-10ce4cde04bb']['InstallationDir'] = ENV["HOME"]

