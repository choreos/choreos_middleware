##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e7064f9b-183d-4134-b140-e77eb2951915 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e7064f9b-183d-4134-b140-e77eb2951915']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['e7064f9b-183d-4134-b140-e77eb2951915']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e7064f9b-183d-4134-b140-e77eb2951915']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e7064f9b-183d-4134-b140-e77eb2951915']['InstallationDir'] = ENV["HOME"]

