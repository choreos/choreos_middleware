##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 89cb5a90-945f-41a6-a2f5-7d353bad33cb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['89cb5a90-945f-41a6-a2f5-7d353bad33cb']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['89cb5a90-945f-41a6-a2f5-7d353bad33cb']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['89cb5a90-945f-41a6-a2f5-7d353bad33cb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['89cb5a90-945f-41a6-a2f5-7d353bad33cb']['InstallationDir'] = ENV["HOME"]

