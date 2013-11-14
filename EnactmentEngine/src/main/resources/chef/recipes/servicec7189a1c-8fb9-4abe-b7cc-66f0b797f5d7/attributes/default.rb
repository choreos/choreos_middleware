##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c7189a1c-8fb9-4abe-b7cc-66f0b797f5d7']['InstallationDir'] = ENV["HOME"]

