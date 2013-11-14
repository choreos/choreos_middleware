##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of aaa79dd7-06e8-40d9-b8db-65639070c7d3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['aaa79dd7-06e8-40d9-b8db-65639070c7d3']['InstallationDir'] = ENV["HOME"]

