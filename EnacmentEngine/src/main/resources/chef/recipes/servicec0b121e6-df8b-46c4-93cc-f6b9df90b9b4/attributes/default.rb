##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c0b121e6-df8b-46c4-93cc-f6b9df90b9b4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c0b121e6-df8b-46c4-93cc-f6b9df90b9b4']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['c0b121e6-df8b-46c4-93cc-f6b9df90b9b4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c0b121e6-df8b-46c4-93cc-f6b9df90b9b4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c0b121e6-df8b-46c4-93cc-f6b9df90b9b4']['InstallationDir'] = ENV["HOME"]

