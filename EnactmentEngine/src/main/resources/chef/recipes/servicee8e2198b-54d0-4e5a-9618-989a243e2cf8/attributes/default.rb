##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e8e2198b-54d0-4e5a-9618-989a243e2cf8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e8e2198b-54d0-4e5a-9618-989a243e2cf8']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['e8e2198b-54d0-4e5a-9618-989a243e2cf8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e8e2198b-54d0-4e5a-9618-989a243e2cf8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e8e2198b-54d0-4e5a-9618-989a243e2cf8']['InstallationDir'] = ENV["HOME"]

