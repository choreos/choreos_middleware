##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 00eb0d89-87e7-44cf-93ce-e4da66196599 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['00eb0d89-87e7-44cf-93ce-e4da66196599']['WarFile'] = ""
default['CHOReOSData']['serviceData']['00eb0d89-87e7-44cf-93ce-e4da66196599']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['00eb0d89-87e7-44cf-93ce-e4da66196599']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['00eb0d89-87e7-44cf-93ce-e4da66196599']['InstallationDir'] = ENV["HOME"]

