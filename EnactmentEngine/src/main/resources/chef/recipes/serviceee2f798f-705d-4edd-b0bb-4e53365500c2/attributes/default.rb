##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ee2f798f-705d-4edd-b0bb-4e53365500c2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ee2f798f-705d-4edd-b0bb-4e53365500c2']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['ee2f798f-705d-4edd-b0bb-4e53365500c2']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ee2f798f-705d-4edd-b0bb-4e53365500c2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ee2f798f-705d-4edd-b0bb-4e53365500c2']['InstallationDir'] = ENV["HOME"]

