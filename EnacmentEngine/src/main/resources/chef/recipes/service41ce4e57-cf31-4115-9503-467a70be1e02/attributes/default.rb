##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 41ce4e57-cf31-4115-9503-467a70be1e02 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['41ce4e57-cf31-4115-9503-467a70be1e02']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['41ce4e57-cf31-4115-9503-467a70be1e02']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['41ce4e57-cf31-4115-9503-467a70be1e02']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['41ce4e57-cf31-4115-9503-467a70be1e02']['InstallationDir'] = ENV["HOME"]

