##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 38456b42-906b-4185-a710-9d979ca32480 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['38456b42-906b-4185-a710-9d979ca32480']['WarFile'] = ""
default['CHOReOSData']['serviceData']['38456b42-906b-4185-a710-9d979ca32480']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['38456b42-906b-4185-a710-9d979ca32480']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['38456b42-906b-4185-a710-9d979ca32480']['InstallationDir'] = ENV["HOME"]

