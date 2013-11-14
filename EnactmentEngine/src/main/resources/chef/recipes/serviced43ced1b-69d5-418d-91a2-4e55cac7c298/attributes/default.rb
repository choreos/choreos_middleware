##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d43ced1b-69d5-418d-91a2-4e55cac7c298 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d43ced1b-69d5-418d-91a2-4e55cac7c298']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['d43ced1b-69d5-418d-91a2-4e55cac7c298']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d43ced1b-69d5-418d-91a2-4e55cac7c298']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d43ced1b-69d5-418d-91a2-4e55cac7c298']['InstallationDir'] = ENV["HOME"]

