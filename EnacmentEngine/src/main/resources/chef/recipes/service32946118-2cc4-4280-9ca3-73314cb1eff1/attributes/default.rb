##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 32946118-2cc4-4280-9ca3-73314cb1eff1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['32946118-2cc4-4280-9ca3-73314cb1eff1']['WarFile'] = ""
default['CHOReOSData']['serviceData']['32946118-2cc4-4280-9ca3-73314cb1eff1']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['32946118-2cc4-4280-9ca3-73314cb1eff1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['32946118-2cc4-4280-9ca3-73314cb1eff1']['InstallationDir'] = ENV["HOME"]
