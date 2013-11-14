##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4cbbec11-5750-468a-adf9-4a8cf470bc56 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4cbbec11-5750-468a-adf9-4a8cf470bc56']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['4cbbec11-5750-468a-adf9-4a8cf470bc56']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4cbbec11-5750-468a-adf9-4a8cf470bc56']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4cbbec11-5750-468a-adf9-4a8cf470bc56']['InstallationDir'] = ENV["HOME"]

