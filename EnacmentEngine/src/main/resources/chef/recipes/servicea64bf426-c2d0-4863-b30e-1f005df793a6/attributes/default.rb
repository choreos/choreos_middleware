##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a64bf426-c2d0-4863-b30e-1f005df793a6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a64bf426-c2d0-4863-b30e-1f005df793a6']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['a64bf426-c2d0-4863-b30e-1f005df793a6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a64bf426-c2d0-4863-b30e-1f005df793a6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a64bf426-c2d0-4863-b30e-1f005df793a6']['InstallationDir'] = ENV["HOME"]

