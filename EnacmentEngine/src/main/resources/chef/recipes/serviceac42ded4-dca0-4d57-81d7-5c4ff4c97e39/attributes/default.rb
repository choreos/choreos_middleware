##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ac42ded4-dca0-4d57-81d7-5c4ff4c97e39 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ac42ded4-dca0-4d57-81d7-5c4ff4c97e39']['WarFile'] = ""
default['CHOReOSData']['serviceData']['ac42ded4-dca0-4d57-81d7-5c4ff4c97e39']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ac42ded4-dca0-4d57-81d7-5c4ff4c97e39']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ac42ded4-dca0-4d57-81d7-5c4ff4c97e39']['InstallationDir'] = ENV["HOME"]

