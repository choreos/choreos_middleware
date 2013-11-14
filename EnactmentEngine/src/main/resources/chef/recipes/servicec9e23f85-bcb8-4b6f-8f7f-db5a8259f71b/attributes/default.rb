##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b']['WarFile'] = ""
default['CHOReOSData']['serviceData']['c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c9e23f85-bcb8-4b6f-8f7f-db5a8259f71b']['InstallationDir'] = ENV["HOME"]

