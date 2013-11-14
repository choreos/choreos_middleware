##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of faf28b9b-6f14-4585-8b7e-397b2797098b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['faf28b9b-6f14-4585-8b7e-397b2797098b']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['faf28b9b-6f14-4585-8b7e-397b2797098b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['faf28b9b-6f14-4585-8b7e-397b2797098b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['faf28b9b-6f14-4585-8b7e-397b2797098b']['InstallationDir'] = ENV["HOME"]

