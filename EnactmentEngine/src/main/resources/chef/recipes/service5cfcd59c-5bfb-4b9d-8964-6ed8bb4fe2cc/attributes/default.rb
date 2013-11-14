##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc']['InstallationDir'] = ENV["HOME"]

