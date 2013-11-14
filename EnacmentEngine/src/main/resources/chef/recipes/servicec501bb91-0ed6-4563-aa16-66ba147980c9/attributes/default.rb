##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c501bb91-0ed6-4563-aa16-66ba147980c9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c501bb91-0ed6-4563-aa16-66ba147980c9']['WarFile'] = ""
default['CHOReOSData']['serviceData']['c501bb91-0ed6-4563-aa16-66ba147980c9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c501bb91-0ed6-4563-aa16-66ba147980c9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c501bb91-0ed6-4563-aa16-66ba147980c9']['InstallationDir'] = ENV["HOME"]

