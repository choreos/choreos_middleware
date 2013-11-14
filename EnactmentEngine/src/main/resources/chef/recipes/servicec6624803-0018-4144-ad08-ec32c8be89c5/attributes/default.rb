##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c6624803-0018-4144-ad08-ec32c8be89c5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c6624803-0018-4144-ad08-ec32c8be89c5']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['c6624803-0018-4144-ad08-ec32c8be89c5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c6624803-0018-4144-ad08-ec32c8be89c5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c6624803-0018-4144-ad08-ec32c8be89c5']['InstallationDir'] = ENV["HOME"]

