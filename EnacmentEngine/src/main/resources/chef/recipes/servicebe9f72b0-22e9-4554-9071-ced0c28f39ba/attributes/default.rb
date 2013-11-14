##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of be9f72b0-22e9-4554-9071-ced0c28f39ba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['be9f72b0-22e9-4554-9071-ced0c28f39ba']['InstallationDir'] = ENV["HOME"]

