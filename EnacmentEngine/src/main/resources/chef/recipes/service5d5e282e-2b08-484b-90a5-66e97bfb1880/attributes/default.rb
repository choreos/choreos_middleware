##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5d5e282e-2b08-484b-90a5-66e97bfb1880 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5d5e282e-2b08-484b-90a5-66e97bfb1880']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['5d5e282e-2b08-484b-90a5-66e97bfb1880']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5d5e282e-2b08-484b-90a5-66e97bfb1880']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5d5e282e-2b08-484b-90a5-66e97bfb1880']['InstallationDir'] = ENV["HOME"]

