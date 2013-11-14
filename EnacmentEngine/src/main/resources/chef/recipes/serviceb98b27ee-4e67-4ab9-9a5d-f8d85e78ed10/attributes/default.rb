##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10']['WarFile'] = ""
default['CHOReOSData']['serviceData']['b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b98b27ee-4e67-4ab9-9a5d-f8d85e78ed10']['InstallationDir'] = ENV["HOME"]

