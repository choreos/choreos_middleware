##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a4ba263e-683c-4224-a98e-1a3d264599c9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a4ba263e-683c-4224-a98e-1a3d264599c9']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['a4ba263e-683c-4224-a98e-1a3d264599c9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a4ba263e-683c-4224-a98e-1a3d264599c9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a4ba263e-683c-4224-a98e-1a3d264599c9']['InstallationDir'] = ENV["HOME"]

