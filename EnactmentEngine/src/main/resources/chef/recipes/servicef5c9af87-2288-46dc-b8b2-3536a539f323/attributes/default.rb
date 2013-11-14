##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f5c9af87-2288-46dc-b8b2-3536a539f323 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f5c9af87-2288-46dc-b8b2-3536a539f323']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['f5c9af87-2288-46dc-b8b2-3536a539f323']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f5c9af87-2288-46dc-b8b2-3536a539f323']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f5c9af87-2288-46dc-b8b2-3536a539f323']['InstallationDir'] = ENV["HOME"]

