##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 59271e33-b1b4-4b10-bf00-ee589f90d92d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['59271e33-b1b4-4b10-bf00-ee589f90d92d']['InstallationDir'] = ENV["HOME"]

