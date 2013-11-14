##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1ad1d9fb-b1f2-4d9b-acfa-3f8a7caacda3']['InstallationDir'] = ENV["HOME"]

