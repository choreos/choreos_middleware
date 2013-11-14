##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of da7186c4-a6b1-46d2-b2b6-7c7ed785c953 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['da7186c4-a6b1-46d2-b2b6-7c7ed785c953']['InstallationDir'] = ENV["HOME"]

