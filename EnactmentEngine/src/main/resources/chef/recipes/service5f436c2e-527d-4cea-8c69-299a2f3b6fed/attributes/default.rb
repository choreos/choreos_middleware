##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5f436c2e-527d-4cea-8c69-299a2f3b6fed must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5f436c2e-527d-4cea-8c69-299a2f3b6fed']['InstallationDir'] = ENV["HOME"]

