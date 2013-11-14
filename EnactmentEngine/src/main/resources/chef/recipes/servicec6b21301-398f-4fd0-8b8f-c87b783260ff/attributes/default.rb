##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c6b21301-398f-4fd0-8b8f-c87b783260ff must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c6b21301-398f-4fd0-8b8f-c87b783260ff']['InstallationDir'] = ENV["HOME"]

