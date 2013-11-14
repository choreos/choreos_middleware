##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of da66e508-c56a-402d-9f6c-1ddc2a26d0e8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['da66e508-c56a-402d-9f6c-1ddc2a26d0e8']['InstallationDir'] = ENV["HOME"]

