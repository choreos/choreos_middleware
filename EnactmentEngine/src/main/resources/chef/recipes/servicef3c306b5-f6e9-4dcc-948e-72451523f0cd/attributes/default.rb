##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f3c306b5-f6e9-4dcc-948e-72451523f0cd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f3c306b5-f6e9-4dcc-948e-72451523f0cd']['InstallationDir'] = ENV["HOME"]

