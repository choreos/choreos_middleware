##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7cb8cc32-b6e4-4645-874e-d463705a55f7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7cb8cc32-b6e4-4645-874e-d463705a55f7']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7cb8cc32-b6e4-4645-874e-d463705a55f7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7cb8cc32-b6e4-4645-874e-d463705a55f7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7cb8cc32-b6e4-4645-874e-d463705a55f7']['InstallationDir'] = ENV["HOME"]

