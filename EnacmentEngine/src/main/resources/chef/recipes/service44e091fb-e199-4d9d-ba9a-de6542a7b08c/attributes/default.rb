##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 44e091fb-e199-4d9d-ba9a-de6542a7b08c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['44e091fb-e199-4d9d-ba9a-de6542a7b08c']['WarFile'] = ""
default['CHOReOSData']['serviceData']['44e091fb-e199-4d9d-ba9a-de6542a7b08c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['44e091fb-e199-4d9d-ba9a-de6542a7b08c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['44e091fb-e199-4d9d-ba9a-de6542a7b08c']['InstallationDir'] = ENV["HOME"]

