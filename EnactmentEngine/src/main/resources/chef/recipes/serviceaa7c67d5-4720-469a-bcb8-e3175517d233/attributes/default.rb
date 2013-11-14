##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of aa7c67d5-4720-469a-bcb8-e3175517d233 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['aa7c67d5-4720-469a-bcb8-e3175517d233']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['aa7c67d5-4720-469a-bcb8-e3175517d233']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['aa7c67d5-4720-469a-bcb8-e3175517d233']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['aa7c67d5-4720-469a-bcb8-e3175517d233']['InstallationDir'] = ENV["HOME"]

