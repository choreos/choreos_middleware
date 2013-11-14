##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c5e15a38-a8b2-4945-9e0e-5dda4d46b02c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c5e15a38-a8b2-4945-9e0e-5dda4d46b02c']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['c5e15a38-a8b2-4945-9e0e-5dda4d46b02c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c5e15a38-a8b2-4945-9e0e-5dda4d46b02c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c5e15a38-a8b2-4945-9e0e-5dda4d46b02c']['InstallationDir'] = ENV["HOME"]

