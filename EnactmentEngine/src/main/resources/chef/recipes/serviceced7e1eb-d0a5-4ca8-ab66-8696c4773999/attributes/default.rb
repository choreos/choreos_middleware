##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ced7e1eb-d0a5-4ca8-ab66-8696c4773999 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ced7e1eb-d0a5-4ca8-ab66-8696c4773999']['WarFile'] = ""
default['CHOReOSData']['serviceData']['ced7e1eb-d0a5-4ca8-ab66-8696c4773999']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ced7e1eb-d0a5-4ca8-ab66-8696c4773999']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ced7e1eb-d0a5-4ca8-ab66-8696c4773999']['InstallationDir'] = ENV["HOME"]

