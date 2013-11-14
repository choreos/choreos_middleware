##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ceb11533-4a66-499a-824b-669f856500c8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ceb11533-4a66-499a-824b-669f856500c8']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['ceb11533-4a66-499a-824b-669f856500c8']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ceb11533-4a66-499a-824b-669f856500c8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ceb11533-4a66-499a-824b-669f856500c8']['InstallationDir'] = ENV["HOME"]

