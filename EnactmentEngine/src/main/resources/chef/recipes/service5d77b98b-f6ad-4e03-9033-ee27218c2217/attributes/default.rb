##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5d77b98b-f6ad-4e03-9033-ee27218c2217 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5d77b98b-f6ad-4e03-9033-ee27218c2217']['WarFile'] = ""
default['CHOReOSData']['serviceData']['5d77b98b-f6ad-4e03-9033-ee27218c2217']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5d77b98b-f6ad-4e03-9033-ee27218c2217']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5d77b98b-f6ad-4e03-9033-ee27218c2217']['InstallationDir'] = ENV["HOME"]

