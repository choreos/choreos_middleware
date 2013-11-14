##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['09d60b5d-f6ec-4ceb-b9e8-a2d549f4377d']['InstallationDir'] = ENV["HOME"]

