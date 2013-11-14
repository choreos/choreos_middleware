##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1f5dcdb3-ea92-4237-8c80-7f758bc59eb9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1f5dcdb3-ea92-4237-8c80-7f758bc59eb9']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['1f5dcdb3-ea92-4237-8c80-7f758bc59eb9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1f5dcdb3-ea92-4237-8c80-7f758bc59eb9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1f5dcdb3-ea92-4237-8c80-7f758bc59eb9']['InstallationDir'] = ENV["HOME"]

