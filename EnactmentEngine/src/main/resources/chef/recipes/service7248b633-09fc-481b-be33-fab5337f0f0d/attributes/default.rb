##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7248b633-09fc-481b-be33-fab5337f0f0d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7248b633-09fc-481b-be33-fab5337f0f0d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7248b633-09fc-481b-be33-fab5337f0f0d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7248b633-09fc-481b-be33-fab5337f0f0d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7248b633-09fc-481b-be33-fab5337f0f0d']['InstallationDir'] = ENV["HOME"]

