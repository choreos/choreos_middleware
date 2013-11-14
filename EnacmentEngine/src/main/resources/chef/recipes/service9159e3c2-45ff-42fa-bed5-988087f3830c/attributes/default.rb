##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9159e3c2-45ff-42fa-bed5-988087f3830c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9159e3c2-45ff-42fa-bed5-988087f3830c']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['9159e3c2-45ff-42fa-bed5-988087f3830c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9159e3c2-45ff-42fa-bed5-988087f3830c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9159e3c2-45ff-42fa-bed5-988087f3830c']['InstallationDir'] = ENV["HOME"]

