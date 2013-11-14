##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8a3fa852-69b6-41cb-9dc6-aebf0618a776 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8a3fa852-69b6-41cb-9dc6-aebf0618a776']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['8a3fa852-69b6-41cb-9dc6-aebf0618a776']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8a3fa852-69b6-41cb-9dc6-aebf0618a776']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8a3fa852-69b6-41cb-9dc6-aebf0618a776']['InstallationDir'] = ENV["HOME"]

