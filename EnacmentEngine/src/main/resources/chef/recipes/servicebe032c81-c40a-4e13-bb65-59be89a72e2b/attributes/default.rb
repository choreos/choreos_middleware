##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of be032c81-c40a-4e13-bb65-59be89a72e2b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['be032c81-c40a-4e13-bb65-59be89a72e2b']['InstallationDir'] = ENV["HOME"]

