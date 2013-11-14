##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e8e2272d-2fb8-45ed-b9a1-4878fb29d253 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e8e2272d-2fb8-45ed-b9a1-4878fb29d253']['WarFile'] = ""
default['CHOReOSData']['serviceData']['e8e2272d-2fb8-45ed-b9a1-4878fb29d253']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e8e2272d-2fb8-45ed-b9a1-4878fb29d253']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e8e2272d-2fb8-45ed-b9a1-4878fb29d253']['InstallationDir'] = ENV["HOME"]

