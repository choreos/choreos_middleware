##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ee91185e-8cb5-47fc-b9f4-e445f31c3c81 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ee91185e-8cb5-47fc-b9f4-e445f31c3c81']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['ee91185e-8cb5-47fc-b9f4-e445f31c3c81']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ee91185e-8cb5-47fc-b9f4-e445f31c3c81']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ee91185e-8cb5-47fc-b9f4-e445f31c3c81']['InstallationDir'] = ENV["HOME"]

