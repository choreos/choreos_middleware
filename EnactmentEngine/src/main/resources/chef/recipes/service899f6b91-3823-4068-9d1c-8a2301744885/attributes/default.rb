##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 899f6b91-3823-4068-9d1c-8a2301744885 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['899f6b91-3823-4068-9d1c-8a2301744885']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['899f6b91-3823-4068-9d1c-8a2301744885']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['899f6b91-3823-4068-9d1c-8a2301744885']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['899f6b91-3823-4068-9d1c-8a2301744885']['InstallationDir'] = ENV["HOME"]

