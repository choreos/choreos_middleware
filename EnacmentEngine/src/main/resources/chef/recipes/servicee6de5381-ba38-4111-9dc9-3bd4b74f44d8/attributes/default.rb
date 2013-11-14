##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e6de5381-ba38-4111-9dc9-3bd4b74f44d8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e6de5381-ba38-4111-9dc9-3bd4b74f44d8']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e6de5381-ba38-4111-9dc9-3bd4b74f44d8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e6de5381-ba38-4111-9dc9-3bd4b74f44d8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e6de5381-ba38-4111-9dc9-3bd4b74f44d8']['InstallationDir'] = ENV["HOME"]

