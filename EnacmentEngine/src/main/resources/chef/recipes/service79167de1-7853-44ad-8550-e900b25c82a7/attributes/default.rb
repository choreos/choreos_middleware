##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 79167de1-7853-44ad-8550-e900b25c82a7 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['79167de1-7853-44ad-8550-e900b25c82a7']['WarFile'] = ""
default['CHOReOSData']['serviceData']['79167de1-7853-44ad-8550-e900b25c82a7']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['79167de1-7853-44ad-8550-e900b25c82a7']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['79167de1-7853-44ad-8550-e900b25c82a7']['InstallationDir'] = ENV["HOME"]

