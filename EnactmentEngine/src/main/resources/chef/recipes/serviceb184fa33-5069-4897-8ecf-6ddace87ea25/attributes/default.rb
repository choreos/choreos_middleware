##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b184fa33-5069-4897-8ecf-6ddace87ea25 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b184fa33-5069-4897-8ecf-6ddace87ea25']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['b184fa33-5069-4897-8ecf-6ddace87ea25']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b184fa33-5069-4897-8ecf-6ddace87ea25']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b184fa33-5069-4897-8ecf-6ddace87ea25']['InstallationDir'] = ENV["HOME"]

