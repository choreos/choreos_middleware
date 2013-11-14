##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 88db6429-06fe-4238-b8df-f37fd0ba5e4f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['88db6429-06fe-4238-b8df-f37fd0ba5e4f']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['88db6429-06fe-4238-b8df-f37fd0ba5e4f']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['88db6429-06fe-4238-b8df-f37fd0ba5e4f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['88db6429-06fe-4238-b8df-f37fd0ba5e4f']['InstallationDir'] = ENV["HOME"]

