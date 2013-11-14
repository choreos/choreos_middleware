##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3cd765ff-a2cb-4913-9996-465f9ab6e67c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3cd765ff-a2cb-4913-9996-465f9ab6e67c']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['3cd765ff-a2cb-4913-9996-465f9ab6e67c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3cd765ff-a2cb-4913-9996-465f9ab6e67c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3cd765ff-a2cb-4913-9996-465f9ab6e67c']['InstallationDir'] = ENV["HOME"]

