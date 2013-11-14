##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c909df1b-ea4a-41d1-a255-c252eda3f80d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c909df1b-ea4a-41d1-a255-c252eda3f80d']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['c909df1b-ea4a-41d1-a255-c252eda3f80d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c909df1b-ea4a-41d1-a255-c252eda3f80d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c909df1b-ea4a-41d1-a255-c252eda3f80d']['InstallationDir'] = ENV["HOME"]

