##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f083356e-6414-496a-97f6-23fed17345df must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f083356e-6414-496a-97f6-23fed17345df']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['f083356e-6414-496a-97f6-23fed17345df']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f083356e-6414-496a-97f6-23fed17345df']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f083356e-6414-496a-97f6-23fed17345df']['InstallationDir'] = ENV["HOME"]

