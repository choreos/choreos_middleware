##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f7ae5cba-6c05-4a5b-97b7-69de91df1acc must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f7ae5cba-6c05-4a5b-97b7-69de91df1acc']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['f7ae5cba-6c05-4a5b-97b7-69de91df1acc']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f7ae5cba-6c05-4a5b-97b7-69de91df1acc']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f7ae5cba-6c05-4a5b-97b7-69de91df1acc']['InstallationDir'] = ENV["HOME"]

