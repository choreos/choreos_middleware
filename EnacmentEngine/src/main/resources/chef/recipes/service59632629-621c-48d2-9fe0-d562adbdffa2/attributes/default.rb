##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 59632629-621c-48d2-9fe0-d562adbdffa2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['59632629-621c-48d2-9fe0-d562adbdffa2']['WarFile'] = ""
default['CHOReOSData']['serviceData']['59632629-621c-48d2-9fe0-d562adbdffa2']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['59632629-621c-48d2-9fe0-d562adbdffa2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['59632629-621c-48d2-9fe0-d562adbdffa2']['InstallationDir'] = ENV["HOME"]

