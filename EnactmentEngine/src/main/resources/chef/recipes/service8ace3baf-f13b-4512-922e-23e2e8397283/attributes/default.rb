##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8ace3baf-f13b-4512-922e-23e2e8397283 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8ace3baf-f13b-4512-922e-23e2e8397283']['WarFile'] = ""
default['CHOReOSData']['serviceData']['8ace3baf-f13b-4512-922e-23e2e8397283']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8ace3baf-f13b-4512-922e-23e2e8397283']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8ace3baf-f13b-4512-922e-23e2e8397283']['InstallationDir'] = ENV["HOME"]

