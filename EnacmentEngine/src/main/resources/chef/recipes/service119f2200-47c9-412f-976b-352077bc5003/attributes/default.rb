##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 119f2200-47c9-412f-976b-352077bc5003 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['119f2200-47c9-412f-976b-352077bc5003']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['119f2200-47c9-412f-976b-352077bc5003']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['119f2200-47c9-412f-976b-352077bc5003']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['119f2200-47c9-412f-976b-352077bc5003']['InstallationDir'] = ENV["HOME"]

