##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 057f78a3-c5c5-4166-b26e-02b7e5daabef must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['057f78a3-c5c5-4166-b26e-02b7e5daabef']['InstallationDir'] = ENV["HOME"]

