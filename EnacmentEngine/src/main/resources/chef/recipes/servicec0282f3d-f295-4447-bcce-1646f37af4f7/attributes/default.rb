##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c0282f3d-f295-4447-bcce-1646f37af4f7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c0282f3d-f295-4447-bcce-1646f37af4f7']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['c0282f3d-f295-4447-bcce-1646f37af4f7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c0282f3d-f295-4447-bcce-1646f37af4f7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c0282f3d-f295-4447-bcce-1646f37af4f7']['InstallationDir'] = ENV["HOME"]

