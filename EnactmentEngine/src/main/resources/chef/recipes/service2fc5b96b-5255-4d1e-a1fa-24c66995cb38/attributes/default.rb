##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2fc5b96b-5255-4d1e-a1fa-24c66995cb38 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2fc5b96b-5255-4d1e-a1fa-24c66995cb38']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['2fc5b96b-5255-4d1e-a1fa-24c66995cb38']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2fc5b96b-5255-4d1e-a1fa-24c66995cb38']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2fc5b96b-5255-4d1e-a1fa-24c66995cb38']['InstallationDir'] = ENV["HOME"]

