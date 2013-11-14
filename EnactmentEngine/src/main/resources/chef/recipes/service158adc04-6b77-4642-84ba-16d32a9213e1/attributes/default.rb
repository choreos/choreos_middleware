##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 158adc04-6b77-4642-84ba-16d32a9213e1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['158adc04-6b77-4642-84ba-16d32a9213e1']['WarFile'] = ""
default['CHOReOSData']['serviceData']['158adc04-6b77-4642-84ba-16d32a9213e1']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['158adc04-6b77-4642-84ba-16d32a9213e1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['158adc04-6b77-4642-84ba-16d32a9213e1']['InstallationDir'] = ENV["HOME"]

