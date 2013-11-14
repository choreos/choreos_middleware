##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35']['InstallationDir'] = ENV["HOME"]

