##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b04205c9-f629-46e5-8613-a5720f9aec4a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b04205c9-f629-46e5-8613-a5720f9aec4a']['InstallationDir'] = ENV["HOME"]

