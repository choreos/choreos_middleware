##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f047bae7-aedd-46d6-905c-a40cfd8caf0b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f047bae7-aedd-46d6-905c-a40cfd8caf0b']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['f047bae7-aedd-46d6-905c-a40cfd8caf0b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f047bae7-aedd-46d6-905c-a40cfd8caf0b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f047bae7-aedd-46d6-905c-a40cfd8caf0b']['InstallationDir'] = ENV["HOME"]

