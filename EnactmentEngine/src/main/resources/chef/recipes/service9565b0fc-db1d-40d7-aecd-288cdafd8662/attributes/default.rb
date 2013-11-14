##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9565b0fc-db1d-40d7-aecd-288cdafd8662 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9565b0fc-db1d-40d7-aecd-288cdafd8662']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['9565b0fc-db1d-40d7-aecd-288cdafd8662']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9565b0fc-db1d-40d7-aecd-288cdafd8662']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9565b0fc-db1d-40d7-aecd-288cdafd8662']['InstallationDir'] = ENV["HOME"]

