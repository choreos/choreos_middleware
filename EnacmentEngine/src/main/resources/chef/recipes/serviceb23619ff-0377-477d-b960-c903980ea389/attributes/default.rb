##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b23619ff-0377-477d-b960-c903980ea389 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b23619ff-0377-477d-b960-c903980ea389']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['b23619ff-0377-477d-b960-c903980ea389']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b23619ff-0377-477d-b960-c903980ea389']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b23619ff-0377-477d-b960-c903980ea389']['InstallationDir'] = ENV["HOME"]

