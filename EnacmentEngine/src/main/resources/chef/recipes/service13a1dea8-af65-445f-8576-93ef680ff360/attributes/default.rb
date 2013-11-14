##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 13a1dea8-af65-445f-8576-93ef680ff360 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['13a1dea8-af65-445f-8576-93ef680ff360']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['13a1dea8-af65-445f-8576-93ef680ff360']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['13a1dea8-af65-445f-8576-93ef680ff360']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['13a1dea8-af65-445f-8576-93ef680ff360']['InstallationDir'] = ENV["HOME"]

