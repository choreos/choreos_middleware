##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b98b4500-1184-4854-bc95-e47060115456 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b98b4500-1184-4854-bc95-e47060115456']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['b98b4500-1184-4854-bc95-e47060115456']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b98b4500-1184-4854-bc95-e47060115456']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b98b4500-1184-4854-bc95-e47060115456']['InstallationDir'] = ENV["HOME"]

