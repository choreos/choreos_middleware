##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 88794304-e8cc-4e19-8267-3a27ed651f66 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['88794304-e8cc-4e19-8267-3a27ed651f66']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['88794304-e8cc-4e19-8267-3a27ed651f66']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['88794304-e8cc-4e19-8267-3a27ed651f66']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['88794304-e8cc-4e19-8267-3a27ed651f66']['InstallationDir'] = ENV["HOME"]

