##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6f31dfbf-4e19-4311-bf38-b27e291d625b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6f31dfbf-4e19-4311-bf38-b27e291d625b']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['6f31dfbf-4e19-4311-bf38-b27e291d625b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6f31dfbf-4e19-4311-bf38-b27e291d625b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6f31dfbf-4e19-4311-bf38-b27e291d625b']['InstallationDir'] = ENV["HOME"]

