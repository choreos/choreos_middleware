##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c9f9e71e-1589-4638-ad98-6df6f8ea8e94 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c9f9e71e-1589-4638-ad98-6df6f8ea8e94']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['c9f9e71e-1589-4638-ad98-6df6f8ea8e94']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c9f9e71e-1589-4638-ad98-6df6f8ea8e94']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c9f9e71e-1589-4638-ad98-6df6f8ea8e94']['InstallationDir'] = ENV["HOME"]

