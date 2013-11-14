##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fe000264-a817-413a-8dc3-6ce78ecd7ccf must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fe000264-a817-413a-8dc3-6ce78ecd7ccf']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['fe000264-a817-413a-8dc3-6ce78ecd7ccf']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fe000264-a817-413a-8dc3-6ce78ecd7ccf']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fe000264-a817-413a-8dc3-6ce78ecd7ccf']['InstallationDir'] = ENV["HOME"]

