##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a0d3bbe8-9263-4496-b729-ea1cc2a460c7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a0d3bbe8-9263-4496-b729-ea1cc2a460c7']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['a0d3bbe8-9263-4496-b729-ea1cc2a460c7']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a0d3bbe8-9263-4496-b729-ea1cc2a460c7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a0d3bbe8-9263-4496-b729-ea1cc2a460c7']['InstallationDir'] = ENV["HOME"]

