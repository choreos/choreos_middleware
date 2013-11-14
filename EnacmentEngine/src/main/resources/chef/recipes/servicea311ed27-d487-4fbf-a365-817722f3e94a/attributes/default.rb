##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a311ed27-d487-4fbf-a365-817722f3e94a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a311ed27-d487-4fbf-a365-817722f3e94a']['WarFile'] = ""
default['CHOReOSData']['serviceData']['a311ed27-d487-4fbf-a365-817722f3e94a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a311ed27-d487-4fbf-a365-817722f3e94a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a311ed27-d487-4fbf-a365-817722f3e94a']['InstallationDir'] = ENV["HOME"]

