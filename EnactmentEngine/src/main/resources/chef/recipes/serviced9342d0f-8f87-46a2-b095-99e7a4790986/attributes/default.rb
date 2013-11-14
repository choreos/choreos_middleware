##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d9342d0f-8f87-46a2-b095-99e7a4790986 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d9342d0f-8f87-46a2-b095-99e7a4790986']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['d9342d0f-8f87-46a2-b095-99e7a4790986']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d9342d0f-8f87-46a2-b095-99e7a4790986']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d9342d0f-8f87-46a2-b095-99e7a4790986']['InstallationDir'] = ENV["HOME"]

