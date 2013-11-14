##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 18303ce1-d313-4605-b4c2-050865d92857 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['18303ce1-d313-4605-b4c2-050865d92857']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['18303ce1-d313-4605-b4c2-050865d92857']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['18303ce1-d313-4605-b4c2-050865d92857']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['18303ce1-d313-4605-b4c2-050865d92857']['InstallationDir'] = ENV["HOME"]

