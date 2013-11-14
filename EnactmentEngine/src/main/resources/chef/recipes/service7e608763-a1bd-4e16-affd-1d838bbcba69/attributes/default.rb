##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7e608763-a1bd-4e16-affd-1d838bbcba69 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7e608763-a1bd-4e16-affd-1d838bbcba69']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7e608763-a1bd-4e16-affd-1d838bbcba69']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7e608763-a1bd-4e16-affd-1d838bbcba69']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7e608763-a1bd-4e16-affd-1d838bbcba69']['InstallationDir'] = ENV["HOME"]

