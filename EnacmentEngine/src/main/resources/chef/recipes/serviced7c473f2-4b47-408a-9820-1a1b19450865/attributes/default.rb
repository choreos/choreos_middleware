##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d7c473f2-4b47-408a-9820-1a1b19450865 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d7c473f2-4b47-408a-9820-1a1b19450865']['WarFile'] = ""
default['CHOReOSData']['serviceData']['d7c473f2-4b47-408a-9820-1a1b19450865']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d7c473f2-4b47-408a-9820-1a1b19450865']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d7c473f2-4b47-408a-9820-1a1b19450865']['InstallationDir'] = ENV["HOME"]

