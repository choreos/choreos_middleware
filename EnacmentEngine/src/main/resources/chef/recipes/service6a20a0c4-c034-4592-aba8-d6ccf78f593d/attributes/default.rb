##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6a20a0c4-c034-4592-aba8-d6ccf78f593d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6a20a0c4-c034-4592-aba8-d6ccf78f593d']['WarFile'] = ""
default['CHOReOSData']['serviceData']['6a20a0c4-c034-4592-aba8-d6ccf78f593d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6a20a0c4-c034-4592-aba8-d6ccf78f593d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6a20a0c4-c034-4592-aba8-d6ccf78f593d']['InstallationDir'] = ENV["HOME"]

