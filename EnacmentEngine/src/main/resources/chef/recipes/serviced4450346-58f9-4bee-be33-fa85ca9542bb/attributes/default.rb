##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d4450346-58f9-4bee-be33-fa85ca9542bb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d4450346-58f9-4bee-be33-fa85ca9542bb']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['d4450346-58f9-4bee-be33-fa85ca9542bb']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d4450346-58f9-4bee-be33-fa85ca9542bb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d4450346-58f9-4bee-be33-fa85ca9542bb']['InstallationDir'] = ENV["HOME"]

