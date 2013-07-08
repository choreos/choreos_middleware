##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of teste must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['teste']['WarFile'] = "whatever"
default['CHOReOSData']['serviceData']['teste']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"
default['CHOReOSData']['serviceData']['teste']['NumberOfClients'] = 1

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['teste']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['teste']['InstallationDir'] = ENV["HOME"]

