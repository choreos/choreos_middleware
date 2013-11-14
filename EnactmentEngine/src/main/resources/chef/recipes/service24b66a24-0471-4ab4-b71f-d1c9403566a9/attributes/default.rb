##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 24b66a24-0471-4ab4-b71f-d1c9403566a9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['24b66a24-0471-4ab4-b71f-d1c9403566a9']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['24b66a24-0471-4ab4-b71f-d1c9403566a9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['24b66a24-0471-4ab4-b71f-d1c9403566a9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['24b66a24-0471-4ab4-b71f-d1c9403566a9']['InstallationDir'] = ENV["HOME"]

