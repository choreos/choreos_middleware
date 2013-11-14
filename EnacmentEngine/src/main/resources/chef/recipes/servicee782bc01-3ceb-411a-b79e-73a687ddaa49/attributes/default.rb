##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e782bc01-3ceb-411a-b79e-73a687ddaa49 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e782bc01-3ceb-411a-b79e-73a687ddaa49']['InstallationDir'] = ENV["HOME"]

