##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 986ef9c1-dc45-4e26-b045-a86b57aa343c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['986ef9c1-dc45-4e26-b045-a86b57aa343c']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['986ef9c1-dc45-4e26-b045-a86b57aa343c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['986ef9c1-dc45-4e26-b045-a86b57aa343c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['986ef9c1-dc45-4e26-b045-a86b57aa343c']['InstallationDir'] = ENV["HOME"]

