##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of dd9d69b8-234f-4a78-ac3a-1bbb81e4a134 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['dd9d69b8-234f-4a78-ac3a-1bbb81e4a134']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['dd9d69b8-234f-4a78-ac3a-1bbb81e4a134']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['dd9d69b8-234f-4a78-ac3a-1bbb81e4a134']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['dd9d69b8-234f-4a78-ac3a-1bbb81e4a134']['InstallationDir'] = ENV["HOME"]

