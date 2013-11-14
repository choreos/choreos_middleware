##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4b18e93e-933a-497c-b84a-bc8ce1b0f792 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4b18e93e-933a-497c-b84a-bc8ce1b0f792']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['4b18e93e-933a-497c-b84a-bc8ce1b0f792']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4b18e93e-933a-497c-b84a-bc8ce1b0f792']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4b18e93e-933a-497c-b84a-bc8ce1b0f792']['InstallationDir'] = ENV["HOME"]

