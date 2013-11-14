##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7c3e88bc-f135-4100-810e-1514e268a5c9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7c3e88bc-f135-4100-810e-1514e268a5c9']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7c3e88bc-f135-4100-810e-1514e268a5c9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7c3e88bc-f135-4100-810e-1514e268a5c9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7c3e88bc-f135-4100-810e-1514e268a5c9']['InstallationDir'] = ENV["HOME"]

