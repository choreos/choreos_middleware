##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f690b0fc-c456-4ba9-9e00-e2f79cfd15e0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f690b0fc-c456-4ba9-9e00-e2f79cfd15e0']['WarFile'] = ""
default['CHOReOSData']['serviceData']['f690b0fc-c456-4ba9-9e00-e2f79cfd15e0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f690b0fc-c456-4ba9-9e00-e2f79cfd15e0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f690b0fc-c456-4ba9-9e00-e2f79cfd15e0']['InstallationDir'] = ENV["HOME"]

