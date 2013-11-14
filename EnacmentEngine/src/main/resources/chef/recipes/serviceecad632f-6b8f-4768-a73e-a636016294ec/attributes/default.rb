##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ecad632f-6b8f-4768-a73e-a636016294ec must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ecad632f-6b8f-4768-a73e-a636016294ec']['InstallationDir'] = ENV["HOME"]

