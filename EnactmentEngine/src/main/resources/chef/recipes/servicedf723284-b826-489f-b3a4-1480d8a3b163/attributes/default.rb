##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of df723284-b826-489f-b3a4-1480d8a3b163 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['df723284-b826-489f-b3a4-1480d8a3b163']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['df723284-b826-489f-b3a4-1480d8a3b163']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['df723284-b826-489f-b3a4-1480d8a3b163']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['df723284-b826-489f-b3a4-1480d8a3b163']['InstallationDir'] = ENV["HOME"]

