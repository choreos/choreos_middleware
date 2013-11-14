##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 832df111-ea07-46c5-88f6-2aca3853723a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['832df111-ea07-46c5-88f6-2aca3853723a']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['832df111-ea07-46c5-88f6-2aca3853723a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['832df111-ea07-46c5-88f6-2aca3853723a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['832df111-ea07-46c5-88f6-2aca3853723a']['InstallationDir'] = ENV["HOME"]

