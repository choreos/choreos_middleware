##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b5e65fdd-8db6-4053-bd85-4955c52f776c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b5e65fdd-8db6-4053-bd85-4955c52f776c']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['b5e65fdd-8db6-4053-bd85-4955c52f776c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b5e65fdd-8db6-4053-bd85-4955c52f776c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b5e65fdd-8db6-4053-bd85-4955c52f776c']['InstallationDir'] = ENV["HOME"]

