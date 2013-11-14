##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4cb77c9b-abfb-4b18-8027-3a1fd3096ea4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4cb77c9b-abfb-4b18-8027-3a1fd3096ea4']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['4cb77c9b-abfb-4b18-8027-3a1fd3096ea4']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4cb77c9b-abfb-4b18-8027-3a1fd3096ea4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4cb77c9b-abfb-4b18-8027-3a1fd3096ea4']['InstallationDir'] = ENV["HOME"]

