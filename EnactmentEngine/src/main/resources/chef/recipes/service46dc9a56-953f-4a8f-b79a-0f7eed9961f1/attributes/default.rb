##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 46dc9a56-953f-4a8f-b79a-0f7eed9961f1 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['46dc9a56-953f-4a8f-b79a-0f7eed9961f1']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['46dc9a56-953f-4a8f-b79a-0f7eed9961f1']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['46dc9a56-953f-4a8f-b79a-0f7eed9961f1']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['46dc9a56-953f-4a8f-b79a-0f7eed9961f1']['InstallationDir'] = ENV["HOME"]

