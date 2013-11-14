##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4de0bc0d-5f47-445f-94bb-651f9c7a94ff must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4de0bc0d-5f47-445f-94bb-651f9c7a94ff']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['4de0bc0d-5f47-445f-94bb-651f9c7a94ff']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4de0bc0d-5f47-445f-94bb-651f9c7a94ff']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4de0bc0d-5f47-445f-94bb-651f9c7a94ff']['InstallationDir'] = ENV["HOME"]

