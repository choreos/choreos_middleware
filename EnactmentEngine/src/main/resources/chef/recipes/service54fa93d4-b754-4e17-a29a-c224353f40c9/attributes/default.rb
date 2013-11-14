##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 54fa93d4-b754-4e17-a29a-c224353f40c9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['54fa93d4-b754-4e17-a29a-c224353f40c9']['WarFile'] = ""
default['CHOReOSData']['serviceData']['54fa93d4-b754-4e17-a29a-c224353f40c9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['54fa93d4-b754-4e17-a29a-c224353f40c9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['54fa93d4-b754-4e17-a29a-c224353f40c9']['InstallationDir'] = ENV["HOME"]

