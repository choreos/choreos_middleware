##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b394f47e-dce4-4a76-a78e-91a5830d288d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b394f47e-dce4-4a76-a78e-91a5830d288d']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['b394f47e-dce4-4a76-a78e-91a5830d288d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b394f47e-dce4-4a76-a78e-91a5830d288d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b394f47e-dce4-4a76-a78e-91a5830d288d']['InstallationDir'] = ENV["HOME"]

