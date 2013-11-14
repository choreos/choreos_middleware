##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1c8dd567-49c8-4087-928d-bfdb8e95d7f7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1c8dd567-49c8-4087-928d-bfdb8e95d7f7']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1c8dd567-49c8-4087-928d-bfdb8e95d7f7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1c8dd567-49c8-4087-928d-bfdb8e95d7f7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1c8dd567-49c8-4087-928d-bfdb8e95d7f7']['InstallationDir'] = ENV["HOME"]

