##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 861e1fba-1e41-4584-9466-843d2fd99139 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['861e1fba-1e41-4584-9466-843d2fd99139']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['861e1fba-1e41-4584-9466-843d2fd99139']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['861e1fba-1e41-4584-9466-843d2fd99139']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['861e1fba-1e41-4584-9466-843d2fd99139']['InstallationDir'] = ENV["HOME"]

