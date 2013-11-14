##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a157803a-914a-4cd8-ba80-0ccc9c2d58c9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a157803a-914a-4cd8-ba80-0ccc9c2d58c9']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['a157803a-914a-4cd8-ba80-0ccc9c2d58c9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a157803a-914a-4cd8-ba80-0ccc9c2d58c9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a157803a-914a-4cd8-ba80-0ccc9c2d58c9']['InstallationDir'] = ENV["HOME"]

