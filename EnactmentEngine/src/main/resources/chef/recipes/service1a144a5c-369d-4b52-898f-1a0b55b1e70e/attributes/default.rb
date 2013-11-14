##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1a144a5c-369d-4b52-898f-1a0b55b1e70e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1a144a5c-369d-4b52-898f-1a0b55b1e70e']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1a144a5c-369d-4b52-898f-1a0b55b1e70e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1a144a5c-369d-4b52-898f-1a0b55b1e70e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1a144a5c-369d-4b52-898f-1a0b55b1e70e']['InstallationDir'] = ENV["HOME"]

