##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6706e8f2-d57b-4bce-b7d6-db166bac8d43 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6706e8f2-d57b-4bce-b7d6-db166bac8d43']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['6706e8f2-d57b-4bce-b7d6-db166bac8d43']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6706e8f2-d57b-4bce-b7d6-db166bac8d43']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6706e8f2-d57b-4bce-b7d6-db166bac8d43']['InstallationDir'] = ENV["HOME"]

