##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b8fd09d4-b762-466d-8510-9b195e76fdd2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b8fd09d4-b762-466d-8510-9b195e76fdd2']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['b8fd09d4-b762-466d-8510-9b195e76fdd2']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b8fd09d4-b762-466d-8510-9b195e76fdd2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b8fd09d4-b762-466d-8510-9b195e76fdd2']['InstallationDir'] = ENV["HOME"]

