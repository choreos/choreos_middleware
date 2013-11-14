##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 44a68537-8d55-4fb1-9073-b5b01e8cf45e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['44a68537-8d55-4fb1-9073-b5b01e8cf45e']['WarFile'] = ""
default['CHOReOSData']['serviceData']['44a68537-8d55-4fb1-9073-b5b01e8cf45e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['44a68537-8d55-4fb1-9073-b5b01e8cf45e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['44a68537-8d55-4fb1-9073-b5b01e8cf45e']['InstallationDir'] = ENV["HOME"]

