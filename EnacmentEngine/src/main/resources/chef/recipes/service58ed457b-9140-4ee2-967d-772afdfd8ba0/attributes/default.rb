##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 58ed457b-9140-4ee2-967d-772afdfd8ba0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['58ed457b-9140-4ee2-967d-772afdfd8ba0']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['58ed457b-9140-4ee2-967d-772afdfd8ba0']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['58ed457b-9140-4ee2-967d-772afdfd8ba0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['58ed457b-9140-4ee2-967d-772afdfd8ba0']['InstallationDir'] = ENV["HOME"]

