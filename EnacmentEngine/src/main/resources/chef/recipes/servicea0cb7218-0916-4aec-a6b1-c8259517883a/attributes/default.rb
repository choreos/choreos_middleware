##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a0cb7218-0916-4aec-a6b1-c8259517883a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a0cb7218-0916-4aec-a6b1-c8259517883a']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['a0cb7218-0916-4aec-a6b1-c8259517883a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a0cb7218-0916-4aec-a6b1-c8259517883a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a0cb7218-0916-4aec-a6b1-c8259517883a']['InstallationDir'] = ENV["HOME"]

