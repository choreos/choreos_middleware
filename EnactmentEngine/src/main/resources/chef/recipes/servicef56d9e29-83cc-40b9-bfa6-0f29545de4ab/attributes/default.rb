##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f56d9e29-83cc-40b9-bfa6-0f29545de4ab must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f56d9e29-83cc-40b9-bfa6-0f29545de4ab']['WarFile'] = ""
default['CHOReOSData']['serviceData']['f56d9e29-83cc-40b9-bfa6-0f29545de4ab']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f56d9e29-83cc-40b9-bfa6-0f29545de4ab']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f56d9e29-83cc-40b9-bfa6-0f29545de4ab']['InstallationDir'] = ENV["HOME"]

