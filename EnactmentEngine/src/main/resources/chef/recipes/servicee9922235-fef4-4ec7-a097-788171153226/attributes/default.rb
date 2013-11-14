##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e9922235-fef4-4ec7-a097-788171153226 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e9922235-fef4-4ec7-a097-788171153226']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['e9922235-fef4-4ec7-a097-788171153226']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e9922235-fef4-4ec7-a097-788171153226']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e9922235-fef4-4ec7-a097-788171153226']['InstallationDir'] = ENV["HOME"]

