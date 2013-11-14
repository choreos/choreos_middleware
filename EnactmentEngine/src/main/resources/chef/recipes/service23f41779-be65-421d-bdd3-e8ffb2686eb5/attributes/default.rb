##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 23f41779-be65-421d-bdd3-e8ffb2686eb5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['23f41779-be65-421d-bdd3-e8ffb2686eb5']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['23f41779-be65-421d-bdd3-e8ffb2686eb5']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['23f41779-be65-421d-bdd3-e8ffb2686eb5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['23f41779-be65-421d-bdd3-e8ffb2686eb5']['InstallationDir'] = ENV["HOME"]

