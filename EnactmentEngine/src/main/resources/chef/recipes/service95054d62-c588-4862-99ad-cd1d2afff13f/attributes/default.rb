##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 95054d62-c588-4862-99ad-cd1d2afff13f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['95054d62-c588-4862-99ad-cd1d2afff13f']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['95054d62-c588-4862-99ad-cd1d2afff13f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['95054d62-c588-4862-99ad-cd1d2afff13f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['95054d62-c588-4862-99ad-cd1d2afff13f']['InstallationDir'] = ENV["HOME"]

