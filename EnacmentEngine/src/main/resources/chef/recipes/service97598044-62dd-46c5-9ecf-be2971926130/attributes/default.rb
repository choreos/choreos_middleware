##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 97598044-62dd-46c5-9ecf-be2971926130 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['97598044-62dd-46c5-9ecf-be2971926130']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['97598044-62dd-46c5-9ecf-be2971926130']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['97598044-62dd-46c5-9ecf-be2971926130']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['97598044-62dd-46c5-9ecf-be2971926130']['InstallationDir'] = ENV["HOME"]

