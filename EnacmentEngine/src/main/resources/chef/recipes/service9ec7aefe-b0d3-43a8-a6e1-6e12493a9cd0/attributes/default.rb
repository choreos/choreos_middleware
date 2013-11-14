##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9ec7aefe-b0d3-43a8-a6e1-6e12493a9cd0']['InstallationDir'] = ENV["HOME"]
