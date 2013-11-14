##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9e02c22a-a79a-4710-bbba-7d1606b10a54 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9e02c22a-a79a-4710-bbba-7d1606b10a54']['WarFile'] = ""
default['CHOReOSData']['serviceData']['9e02c22a-a79a-4710-bbba-7d1606b10a54']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9e02c22a-a79a-4710-bbba-7d1606b10a54']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9e02c22a-a79a-4710-bbba-7d1606b10a54']['InstallationDir'] = ENV["HOME"]

