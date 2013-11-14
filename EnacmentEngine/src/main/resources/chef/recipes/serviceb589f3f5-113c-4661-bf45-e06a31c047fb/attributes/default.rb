##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b589f3f5-113c-4661-bf45-e06a31c047fb must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b589f3f5-113c-4661-bf45-e06a31c047fb']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['b589f3f5-113c-4661-bf45-e06a31c047fb']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b589f3f5-113c-4661-bf45-e06a31c047fb']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b589f3f5-113c-4661-bf45-e06a31c047fb']['InstallationDir'] = ENV["HOME"]
