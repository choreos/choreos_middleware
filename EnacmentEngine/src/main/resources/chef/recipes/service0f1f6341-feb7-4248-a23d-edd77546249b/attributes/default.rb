##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0f1f6341-feb7-4248-a23d-edd77546249b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0f1f6341-feb7-4248-a23d-edd77546249b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['0f1f6341-feb7-4248-a23d-edd77546249b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0f1f6341-feb7-4248-a23d-edd77546249b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0f1f6341-feb7-4248-a23d-edd77546249b']['InstallationDir'] = ENV["HOME"]

