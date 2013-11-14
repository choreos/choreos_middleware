##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b9e0b65b-8f0b-418b-abc8-98a4e7a7aee0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b9e0b65b-8f0b-418b-abc8-98a4e7a7aee0']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['b9e0b65b-8f0b-418b-abc8-98a4e7a7aee0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b9e0b65b-8f0b-418b-abc8-98a4e7a7aee0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b9e0b65b-8f0b-418b-abc8-98a4e7a7aee0']['InstallationDir'] = ENV["HOME"]

