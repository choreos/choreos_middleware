##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 84a5703b-23b8-404c-b5de-188bf3ee7aa1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['84a5703b-23b8-404c-b5de-188bf3ee7aa1']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['84a5703b-23b8-404c-b5de-188bf3ee7aa1']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['84a5703b-23b8-404c-b5de-188bf3ee7aa1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['84a5703b-23b8-404c-b5de-188bf3ee7aa1']['InstallationDir'] = ENV["HOME"]

