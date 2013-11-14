##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ec807701-6356-4c68-bd3c-244f1046fe5d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ec807701-6356-4c68-bd3c-244f1046fe5d']['WarFile'] = ""
default['CHOReOSData']['serviceData']['ec807701-6356-4c68-bd3c-244f1046fe5d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ec807701-6356-4c68-bd3c-244f1046fe5d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ec807701-6356-4c68-bd3c-244f1046fe5d']['InstallationDir'] = ENV["HOME"]

