##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['26bfb9d9-7c9a-4ada-ad61-fc50b46f1cb8']['InstallationDir'] = ENV["HOME"]

