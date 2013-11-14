##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 934ce62f-7b42-41af-88b3-a6409c19c5f6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['WarFile'] = ""
default['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['InstallationDir'] = ENV["HOME"]

