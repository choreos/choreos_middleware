##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fd2833cf-4a91-48ed-944f-380a812559aa must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fd2833cf-4a91-48ed-944f-380a812559aa']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['fd2833cf-4a91-48ed-944f-380a812559aa']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fd2833cf-4a91-48ed-944f-380a812559aa']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fd2833cf-4a91-48ed-944f-380a812559aa']['InstallationDir'] = ENV["HOME"]

