##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of dbd77932-26ef-4e87-9e5b-568745ead78b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['dbd77932-26ef-4e87-9e5b-568745ead78b']['WarFile'] = ""
default['CHOReOSData']['serviceData']['dbd77932-26ef-4e87-9e5b-568745ead78b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['dbd77932-26ef-4e87-9e5b-568745ead78b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['dbd77932-26ef-4e87-9e5b-568745ead78b']['InstallationDir'] = ENV["HOME"]

