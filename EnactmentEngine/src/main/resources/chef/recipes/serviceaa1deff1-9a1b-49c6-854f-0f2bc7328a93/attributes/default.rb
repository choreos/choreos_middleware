##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of aa1deff1-9a1b-49c6-854f-0f2bc7328a93 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['aa1deff1-9a1b-49c6-854f-0f2bc7328a93']['InstallationDir'] = ENV["HOME"]

