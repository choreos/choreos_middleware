##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 836b83ab-0b98-4f85-b411-1f4b0cea5615 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['836b83ab-0b98-4f85-b411-1f4b0cea5615']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['836b83ab-0b98-4f85-b411-1f4b0cea5615']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['836b83ab-0b98-4f85-b411-1f4b0cea5615']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['836b83ab-0b98-4f85-b411-1f4b0cea5615']['InstallationDir'] = ENV["HOME"]

