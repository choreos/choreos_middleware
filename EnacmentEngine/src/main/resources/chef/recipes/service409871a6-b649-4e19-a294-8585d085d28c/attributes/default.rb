##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 409871a6-b649-4e19-a294-8585d085d28c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['409871a6-b649-4e19-a294-8585d085d28c']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['409871a6-b649-4e19-a294-8585d085d28c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['409871a6-b649-4e19-a294-8585d085d28c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['409871a6-b649-4e19-a294-8585d085d28c']['InstallationDir'] = ENV["HOME"]

