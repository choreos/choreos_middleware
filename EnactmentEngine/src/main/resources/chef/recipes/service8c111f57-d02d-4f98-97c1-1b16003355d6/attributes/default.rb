##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8c111f57-d02d-4f98-97c1-1b16003355d6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8c111f57-d02d-4f98-97c1-1b16003355d6']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['8c111f57-d02d-4f98-97c1-1b16003355d6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8c111f57-d02d-4f98-97c1-1b16003355d6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8c111f57-d02d-4f98-97c1-1b16003355d6']['InstallationDir'] = ENV["HOME"]

