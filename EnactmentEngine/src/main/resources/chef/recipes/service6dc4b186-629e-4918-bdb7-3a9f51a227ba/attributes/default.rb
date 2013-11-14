##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6dc4b186-629e-4918-bdb7-3a9f51a227ba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6dc4b186-629e-4918-bdb7-3a9f51a227ba']['WarFile'] = ""
default['CHOReOSData']['serviceData']['6dc4b186-629e-4918-bdb7-3a9f51a227ba']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6dc4b186-629e-4918-bdb7-3a9f51a227ba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6dc4b186-629e-4918-bdb7-3a9f51a227ba']['InstallationDir'] = ENV["HOME"]

