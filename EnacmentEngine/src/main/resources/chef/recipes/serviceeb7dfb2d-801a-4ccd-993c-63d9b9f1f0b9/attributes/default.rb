##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['eb7dfb2d-801a-4ccd-993c-63d9b9f1f0b9']['InstallationDir'] = ENV["HOME"]

