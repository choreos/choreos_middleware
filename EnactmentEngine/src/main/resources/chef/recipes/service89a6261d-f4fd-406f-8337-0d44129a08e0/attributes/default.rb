##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 89a6261d-f4fd-406f-8337-0d44129a08e0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['89a6261d-f4fd-406f-8337-0d44129a08e0']['WarFile'] = ""
default['CHOReOSData']['serviceData']['89a6261d-f4fd-406f-8337-0d44129a08e0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['89a6261d-f4fd-406f-8337-0d44129a08e0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['89a6261d-f4fd-406f-8337-0d44129a08e0']['InstallationDir'] = ENV["HOME"]

