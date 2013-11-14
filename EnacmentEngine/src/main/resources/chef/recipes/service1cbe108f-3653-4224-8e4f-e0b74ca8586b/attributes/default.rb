##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1cbe108f-3653-4224-8e4f-e0b74ca8586b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1cbe108f-3653-4224-8e4f-e0b74ca8586b']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1cbe108f-3653-4224-8e4f-e0b74ca8586b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1cbe108f-3653-4224-8e4f-e0b74ca8586b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1cbe108f-3653-4224-8e4f-e0b74ca8586b']['InstallationDir'] = ENV["HOME"]
