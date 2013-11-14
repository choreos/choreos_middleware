##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2381d610-8f1f-4b1a-b9c6-74a68f55c73b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2381d610-8f1f-4b1a-b9c6-74a68f55c73b']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['2381d610-8f1f-4b1a-b9c6-74a68f55c73b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2381d610-8f1f-4b1a-b9c6-74a68f55c73b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2381d610-8f1f-4b1a-b9c6-74a68f55c73b']['InstallationDir'] = ENV["HOME"]

