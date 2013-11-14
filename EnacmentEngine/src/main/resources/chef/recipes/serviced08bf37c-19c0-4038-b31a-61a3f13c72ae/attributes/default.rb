##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d08bf37c-19c0-4038-b31a-61a3f13c72ae must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d08bf37c-19c0-4038-b31a-61a3f13c72ae']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['d08bf37c-19c0-4038-b31a-61a3f13c72ae']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d08bf37c-19c0-4038-b31a-61a3f13c72ae']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d08bf37c-19c0-4038-b31a-61a3f13c72ae']['InstallationDir'] = ENV["HOME"]

