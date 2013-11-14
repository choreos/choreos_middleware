##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b9f4d913-4243-4a6a-8377-e8e1aa675031 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b9f4d913-4243-4a6a-8377-e8e1aa675031']['WarFile'] = ""
default['CHOReOSData']['serviceData']['b9f4d913-4243-4a6a-8377-e8e1aa675031']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b9f4d913-4243-4a6a-8377-e8e1aa675031']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b9f4d913-4243-4a6a-8377-e8e1aa675031']['InstallationDir'] = ENV["HOME"]

