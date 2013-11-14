##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 061cadbe-9671-4c45-8628-e101e4b01b78 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['061cadbe-9671-4c45-8628-e101e4b01b78']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['061cadbe-9671-4c45-8628-e101e4b01b78']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['061cadbe-9671-4c45-8628-e101e4b01b78']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['061cadbe-9671-4c45-8628-e101e4b01b78']['InstallationDir'] = ENV["HOME"]

