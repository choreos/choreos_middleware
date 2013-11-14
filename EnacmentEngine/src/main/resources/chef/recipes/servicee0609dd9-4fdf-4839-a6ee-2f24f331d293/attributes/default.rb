##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e0609dd9-4fdf-4839-a6ee-2f24f331d293 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e0609dd9-4fdf-4839-a6ee-2f24f331d293']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['e0609dd9-4fdf-4839-a6ee-2f24f331d293']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e0609dd9-4fdf-4839-a6ee-2f24f331d293']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e0609dd9-4fdf-4839-a6ee-2f24f331d293']['InstallationDir'] = ENV["HOME"]

