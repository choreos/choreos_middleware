##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9562a61f-989d-4509-9970-a59ca24ccb38 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9562a61f-989d-4509-9970-a59ca24ccb38']['WarFile'] = ""
default['CHOReOSData']['serviceData']['9562a61f-989d-4509-9970-a59ca24ccb38']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9562a61f-989d-4509-9970-a59ca24ccb38']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9562a61f-989d-4509-9970-a59ca24ccb38']['InstallationDir'] = ENV["HOME"]

