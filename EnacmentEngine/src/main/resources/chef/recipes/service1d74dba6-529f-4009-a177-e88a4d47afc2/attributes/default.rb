##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1d74dba6-529f-4009-a177-e88a4d47afc2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1d74dba6-529f-4009-a177-e88a4d47afc2']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['1d74dba6-529f-4009-a177-e88a4d47afc2']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1d74dba6-529f-4009-a177-e88a4d47afc2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1d74dba6-529f-4009-a177-e88a4d47afc2']['InstallationDir'] = ENV["HOME"]

