##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3c8d70df-d734-459f-851a-cf5e15cbe27d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3c8d70df-d734-459f-851a-cf5e15cbe27d']['WarFile'] = ""
default['CHOReOSData']['serviceData']['3c8d70df-d734-459f-851a-cf5e15cbe27d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3c8d70df-d734-459f-851a-cf5e15cbe27d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3c8d70df-d734-459f-851a-cf5e15cbe27d']['InstallationDir'] = ENV["HOME"]

