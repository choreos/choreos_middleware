##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 63f133ae-d2b6-4e1c-bf29-57807ebecf2c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['63f133ae-d2b6-4e1c-bf29-57807ebecf2c']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['63f133ae-d2b6-4e1c-bf29-57807ebecf2c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['63f133ae-d2b6-4e1c-bf29-57807ebecf2c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['63f133ae-d2b6-4e1c-bf29-57807ebecf2c']['InstallationDir'] = ENV["HOME"]

