##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 299f486e-c47e-4b01-9c4f-15306fc961e3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['299f486e-c47e-4b01-9c4f-15306fc961e3']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['299f486e-c47e-4b01-9c4f-15306fc961e3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['299f486e-c47e-4b01-9c4f-15306fc961e3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['299f486e-c47e-4b01-9c4f-15306fc961e3']['InstallationDir'] = ENV["HOME"]

