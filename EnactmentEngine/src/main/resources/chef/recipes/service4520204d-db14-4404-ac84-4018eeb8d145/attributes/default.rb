##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4520204d-db14-4404-ac84-4018eeb8d145 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4520204d-db14-4404-ac84-4018eeb8d145']['WarFile'] = ""
default['CHOReOSData']['serviceData']['4520204d-db14-4404-ac84-4018eeb8d145']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4520204d-db14-4404-ac84-4018eeb8d145']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4520204d-db14-4404-ac84-4018eeb8d145']['InstallationDir'] = ENV["HOME"]

