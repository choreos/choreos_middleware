##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5a4f5cc1-5ed1-4db1-9ffa-b8bbb94ba0f4']['InstallationDir'] = ENV["HOME"]

