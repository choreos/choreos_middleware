##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 82545771-b5ab-4e0f-81c3-347654683473 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['82545771-b5ab-4e0f-81c3-347654683473']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['82545771-b5ab-4e0f-81c3-347654683473']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['82545771-b5ab-4e0f-81c3-347654683473']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['82545771-b5ab-4e0f-81c3-347654683473']['InstallationDir'] = ENV["HOME"]

