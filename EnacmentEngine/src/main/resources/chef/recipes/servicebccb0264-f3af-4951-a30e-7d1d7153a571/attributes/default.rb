##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of bccb0264-f3af-4951-a30e-7d1d7153a571 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['bccb0264-f3af-4951-a30e-7d1d7153a571']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['bccb0264-f3af-4951-a30e-7d1d7153a571']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['bccb0264-f3af-4951-a30e-7d1d7153a571']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['bccb0264-f3af-4951-a30e-7d1d7153a571']['InstallationDir'] = ENV["HOME"]

