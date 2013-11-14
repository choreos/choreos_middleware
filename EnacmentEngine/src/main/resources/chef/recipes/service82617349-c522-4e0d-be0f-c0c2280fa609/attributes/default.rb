##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 82617349-c522-4e0d-be0f-c0c2280fa609 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['82617349-c522-4e0d-be0f-c0c2280fa609']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['82617349-c522-4e0d-be0f-c0c2280fa609']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['82617349-c522-4e0d-be0f-c0c2280fa609']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['82617349-c522-4e0d-be0f-c0c2280fa609']['InstallationDir'] = ENV["HOME"]

