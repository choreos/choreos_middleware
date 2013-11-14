##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7e798134-184f-4d6a-8bab-e3866fa0881b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7e798134-184f-4d6a-8bab-e3866fa0881b']['InstallationDir'] = ENV["HOME"]

