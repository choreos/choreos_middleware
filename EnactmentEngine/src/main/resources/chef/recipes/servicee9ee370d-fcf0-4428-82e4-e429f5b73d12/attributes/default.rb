##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e9ee370d-fcf0-4428-82e4-e429f5b73d12 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e9ee370d-fcf0-4428-82e4-e429f5b73d12']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e9ee370d-fcf0-4428-82e4-e429f5b73d12']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e9ee370d-fcf0-4428-82e4-e429f5b73d12']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e9ee370d-fcf0-4428-82e4-e429f5b73d12']['InstallationDir'] = ENV["HOME"]

