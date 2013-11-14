##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f2b7636b-4b8e-4653-80b6-c284f5db3cba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f2b7636b-4b8e-4653-80b6-c284f5db3cba']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['f2b7636b-4b8e-4653-80b6-c284f5db3cba']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f2b7636b-4b8e-4653-80b6-c284f5db3cba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f2b7636b-4b8e-4653-80b6-c284f5db3cba']['InstallationDir'] = ENV["HOME"]

