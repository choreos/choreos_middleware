##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4b449ea4-4978-46c7-a0fc-4b9279ec6a9a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4b449ea4-4978-46c7-a0fc-4b9279ec6a9a']['WarFile'] = ""
default['CHOReOSData']['serviceData']['4b449ea4-4978-46c7-a0fc-4b9279ec6a9a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4b449ea4-4978-46c7-a0fc-4b9279ec6a9a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4b449ea4-4978-46c7-a0fc-4b9279ec6a9a']['InstallationDir'] = ENV["HOME"]

