##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d78d77d5-5c95-40bb-9ef7-c688459e2bec must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d78d77d5-5c95-40bb-9ef7-c688459e2bec']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['d78d77d5-5c95-40bb-9ef7-c688459e2bec']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d78d77d5-5c95-40bb-9ef7-c688459e2bec']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d78d77d5-5c95-40bb-9ef7-c688459e2bec']['InstallationDir'] = ENV["HOME"]

