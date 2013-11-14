##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 1050bba6-80c0-4314-bde1-2fe751e7486e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['1050bba6-80c0-4314-bde1-2fe751e7486e']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['1050bba6-80c0-4314-bde1-2fe751e7486e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['1050bba6-80c0-4314-bde1-2fe751e7486e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['1050bba6-80c0-4314-bde1-2fe751e7486e']['InstallationDir'] = ENV["HOME"]

