##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 95edb963-c2b5-469d-b586-38d5ad8f7710 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['95edb963-c2b5-469d-b586-38d5ad8f7710']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['95edb963-c2b5-469d-b586-38d5ad8f7710']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['95edb963-c2b5-469d-b586-38d5ad8f7710']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['95edb963-c2b5-469d-b586-38d5ad8f7710']['InstallationDir'] = ENV["HOME"]

