##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c40b2938-5d50-48a0-b92a-53e349d38499 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c40b2938-5d50-48a0-b92a-53e349d38499']['WarFile'] = ""
default['CHOReOSData']['serviceData']['c40b2938-5d50-48a0-b92a-53e349d38499']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c40b2938-5d50-48a0-b92a-53e349d38499']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c40b2938-5d50-48a0-b92a-53e349d38499']['InstallationDir'] = ENV["HOME"]

