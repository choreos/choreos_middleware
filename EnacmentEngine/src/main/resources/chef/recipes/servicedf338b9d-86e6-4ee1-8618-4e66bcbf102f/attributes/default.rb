##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of df338b9d-86e6-4ee1-8618-4e66bcbf102f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['df338b9d-86e6-4ee1-8618-4e66bcbf102f']['InstallationDir'] = ENV["HOME"]

