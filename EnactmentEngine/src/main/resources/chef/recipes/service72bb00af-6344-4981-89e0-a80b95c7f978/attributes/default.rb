##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 72bb00af-6344-4981-89e0-a80b95c7f978 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['72bb00af-6344-4981-89e0-a80b95c7f978']['WarFile'] = ""
default['CHOReOSData']['serviceData']['72bb00af-6344-4981-89e0-a80b95c7f978']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['72bb00af-6344-4981-89e0-a80b95c7f978']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['72bb00af-6344-4981-89e0-a80b95c7f978']['InstallationDir'] = ENV["HOME"]

