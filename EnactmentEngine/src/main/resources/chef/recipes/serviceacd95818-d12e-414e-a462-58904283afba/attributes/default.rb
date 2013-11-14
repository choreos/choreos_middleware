##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of acd95818-d12e-414e-a462-58904283afba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['acd95818-d12e-414e-a462-58904283afba']['WarFile'] = ""
default['CHOReOSData']['serviceData']['acd95818-d12e-414e-a462-58904283afba']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['acd95818-d12e-414e-a462-58904283afba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['acd95818-d12e-414e-a462-58904283afba']['InstallationDir'] = ENV["HOME"]

