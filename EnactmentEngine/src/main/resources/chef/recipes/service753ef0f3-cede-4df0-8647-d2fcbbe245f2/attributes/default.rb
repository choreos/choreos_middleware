##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 753ef0f3-cede-4df0-8647-d2fcbbe245f2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['753ef0f3-cede-4df0-8647-d2fcbbe245f2']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['753ef0f3-cede-4df0-8647-d2fcbbe245f2']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['753ef0f3-cede-4df0-8647-d2fcbbe245f2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['753ef0f3-cede-4df0-8647-d2fcbbe245f2']['InstallationDir'] = ENV["HOME"]

