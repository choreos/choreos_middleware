##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of edee2270-2980-4416-88d1-bd67b8577531 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['edee2270-2980-4416-88d1-bd67b8577531']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['edee2270-2980-4416-88d1-bd67b8577531']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['edee2270-2980-4416-88d1-bd67b8577531']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['edee2270-2980-4416-88d1-bd67b8577531']['InstallationDir'] = ENV["HOME"]

