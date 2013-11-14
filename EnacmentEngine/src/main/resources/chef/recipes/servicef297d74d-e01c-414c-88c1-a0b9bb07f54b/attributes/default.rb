##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f297d74d-e01c-414c-88c1-a0b9bb07f54b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f297d74d-e01c-414c-88c1-a0b9bb07f54b']['InstallationDir'] = ENV["HOME"]

