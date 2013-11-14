##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 465699fe-2cf4-49ce-b69f-afb938bf7ea4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['465699fe-2cf4-49ce-b69f-afb938bf7ea4']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['465699fe-2cf4-49ce-b69f-afb938bf7ea4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['465699fe-2cf4-49ce-b69f-afb938bf7ea4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['465699fe-2cf4-49ce-b69f-afb938bf7ea4']['InstallationDir'] = ENV["HOME"]

