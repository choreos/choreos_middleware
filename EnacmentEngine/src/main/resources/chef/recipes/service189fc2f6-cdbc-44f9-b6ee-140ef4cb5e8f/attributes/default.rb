##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['189fc2f6-cdbc-44f9-b6ee-140ef4cb5e8f']['InstallationDir'] = ENV["HOME"]

