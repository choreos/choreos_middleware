##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e19f54fa-37f3-4295-990e-b4115dba4391 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e19f54fa-37f3-4295-990e-b4115dba4391']['InstallationDir'] = ENV["HOME"]

