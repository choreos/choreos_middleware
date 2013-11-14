##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0d0bab76-c651-4605-a8da-741e8524a71b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0d0bab76-c651-4605-a8da-741e8524a71b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['0d0bab76-c651-4605-a8da-741e8524a71b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0d0bab76-c651-4605-a8da-741e8524a71b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0d0bab76-c651-4605-a8da-741e8524a71b']['InstallationDir'] = ENV["HOME"]

