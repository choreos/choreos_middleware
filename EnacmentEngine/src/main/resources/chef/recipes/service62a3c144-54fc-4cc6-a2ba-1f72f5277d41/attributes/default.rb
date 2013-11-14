##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 62a3c144-54fc-4cc6-a2ba-1f72f5277d41 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['62a3c144-54fc-4cc6-a2ba-1f72f5277d41']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['62a3c144-54fc-4cc6-a2ba-1f72f5277d41']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['62a3c144-54fc-4cc6-a2ba-1f72f5277d41']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['62a3c144-54fc-4cc6-a2ba-1f72f5277d41']['InstallationDir'] = ENV["HOME"]

