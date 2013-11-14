##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ed0fc73f-7544-4a50-8e0f-89e04ff9de8c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ed0fc73f-7544-4a50-8e0f-89e04ff9de8c']['InstallationDir'] = ENV["HOME"]

