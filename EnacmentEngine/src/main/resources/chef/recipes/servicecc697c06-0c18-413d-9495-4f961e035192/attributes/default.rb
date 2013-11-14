##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of cc697c06-0c18-413d-9495-4f961e035192 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['cc697c06-0c18-413d-9495-4f961e035192']['WarFile'] = ""
default['CHOReOSData']['serviceData']['cc697c06-0c18-413d-9495-4f961e035192']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['cc697c06-0c18-413d-9495-4f961e035192']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['cc697c06-0c18-413d-9495-4f961e035192']['InstallationDir'] = ENV["HOME"]

