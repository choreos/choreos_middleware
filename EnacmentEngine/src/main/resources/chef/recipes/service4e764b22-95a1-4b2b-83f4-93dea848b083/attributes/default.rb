##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4e764b22-95a1-4b2b-83f4-93dea848b083 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4e764b22-95a1-4b2b-83f4-93dea848b083']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['4e764b22-95a1-4b2b-83f4-93dea848b083']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4e764b22-95a1-4b2b-83f4-93dea848b083']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4e764b22-95a1-4b2b-83f4-93dea848b083']['InstallationDir'] = ENV["HOME"]
