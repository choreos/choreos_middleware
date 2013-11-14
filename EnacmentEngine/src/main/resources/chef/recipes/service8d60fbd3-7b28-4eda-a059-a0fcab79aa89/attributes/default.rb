##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8d60fbd3-7b28-4eda-a059-a0fcab79aa89 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8d60fbd3-7b28-4eda-a059-a0fcab79aa89']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['8d60fbd3-7b28-4eda-a059-a0fcab79aa89']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8d60fbd3-7b28-4eda-a059-a0fcab79aa89']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8d60fbd3-7b28-4eda-a059-a0fcab79aa89']['InstallationDir'] = ENV["HOME"]

