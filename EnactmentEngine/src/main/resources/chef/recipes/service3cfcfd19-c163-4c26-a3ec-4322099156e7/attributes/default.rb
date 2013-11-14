##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3cfcfd19-c163-4c26-a3ec-4322099156e7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3cfcfd19-c163-4c26-a3ec-4322099156e7']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['3cfcfd19-c163-4c26-a3ec-4322099156e7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3cfcfd19-c163-4c26-a3ec-4322099156e7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3cfcfd19-c163-4c26-a3ec-4322099156e7']['InstallationDir'] = ENV["HOME"]

