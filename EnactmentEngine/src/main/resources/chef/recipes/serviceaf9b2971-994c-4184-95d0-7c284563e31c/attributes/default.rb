##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of af9b2971-994c-4184-95d0-7c284563e31c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['af9b2971-994c-4184-95d0-7c284563e31c']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['af9b2971-994c-4184-95d0-7c284563e31c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['af9b2971-994c-4184-95d0-7c284563e31c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['af9b2971-994c-4184-95d0-7c284563e31c']['InstallationDir'] = ENV["HOME"]

