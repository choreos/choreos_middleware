##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 37cbd7f5-a707-43be-9d07-bf6f344bd910 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['37cbd7f5-a707-43be-9d07-bf6f344bd910']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['37cbd7f5-a707-43be-9d07-bf6f344bd910']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['37cbd7f5-a707-43be-9d07-bf6f344bd910']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['37cbd7f5-a707-43be-9d07-bf6f344bd910']['InstallationDir'] = ENV["HOME"]

