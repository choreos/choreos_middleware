##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9cc757f1-13c1-456f-86a8-86f9392ac061 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9cc757f1-13c1-456f-86a8-86f9392ac061']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['9cc757f1-13c1-456f-86a8-86f9392ac061']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9cc757f1-13c1-456f-86a8-86f9392ac061']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9cc757f1-13c1-456f-86a8-86f9392ac061']['InstallationDir'] = ENV["HOME"]

