##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6a9954f7-7de3-42c9-90e9-2376f4f098e5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6a9954f7-7de3-42c9-90e9-2376f4f098e5']['InstallationDir'] = ENV["HOME"]

