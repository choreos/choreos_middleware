##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 4af74045-2e9a-49e8-8c66-f949cae707e8 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['4af74045-2e9a-49e8-8c66-f949cae707e8']['InstallationDir'] = ENV["HOME"]

