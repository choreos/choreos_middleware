##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4bbfbe98-c48b-4eb7-9888-6532b2075862 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4bbfbe98-c48b-4eb7-9888-6532b2075862']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['4bbfbe98-c48b-4eb7-9888-6532b2075862']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4bbfbe98-c48b-4eb7-9888-6532b2075862']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4bbfbe98-c48b-4eb7-9888-6532b2075862']['InstallationDir'] = ENV["HOME"]
