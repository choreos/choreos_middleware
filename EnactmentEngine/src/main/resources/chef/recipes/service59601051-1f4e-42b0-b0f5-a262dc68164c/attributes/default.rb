##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 59601051-1f4e-42b0-b0f5-a262dc68164c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['59601051-1f4e-42b0-b0f5-a262dc68164c']['WarFile'] = ""
default['CHOReOSData']['serviceData']['59601051-1f4e-42b0-b0f5-a262dc68164c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['59601051-1f4e-42b0-b0f5-a262dc68164c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['59601051-1f4e-42b0-b0f5-a262dc68164c']['InstallationDir'] = ENV["HOME"]

