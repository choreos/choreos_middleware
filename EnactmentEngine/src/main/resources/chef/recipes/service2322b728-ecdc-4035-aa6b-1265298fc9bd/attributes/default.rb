##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2322b728-ecdc-4035-aa6b-1265298fc9bd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2322b728-ecdc-4035-aa6b-1265298fc9bd']['InstallationDir'] = ENV["HOME"]

