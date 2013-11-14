##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 09105506-a66a-49d3-acea-aa373243ed52 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['09105506-a66a-49d3-acea-aa373243ed52']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['09105506-a66a-49d3-acea-aa373243ed52']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['09105506-a66a-49d3-acea-aa373243ed52']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['09105506-a66a-49d3-acea-aa373243ed52']['InstallationDir'] = ENV["HOME"]

