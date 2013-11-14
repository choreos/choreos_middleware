##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6f15e032-d545-4ee9-85cc-df9897a07d6e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6f15e032-d545-4ee9-85cc-df9897a07d6e']['InstallationDir'] = ENV["HOME"]

