##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9325037e-960d-407e-98c8-c4b4440a2871 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9325037e-960d-407e-98c8-c4b4440a2871']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['9325037e-960d-407e-98c8-c4b4440a2871']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9325037e-960d-407e-98c8-c4b4440a2871']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9325037e-960d-407e-98c8-c4b4440a2871']['InstallationDir'] = ENV["HOME"]

