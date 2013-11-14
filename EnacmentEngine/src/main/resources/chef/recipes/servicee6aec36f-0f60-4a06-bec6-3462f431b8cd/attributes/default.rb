##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e6aec36f-0f60-4a06-bec6-3462f431b8cd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e6aec36f-0f60-4a06-bec6-3462f431b8cd']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e6aec36f-0f60-4a06-bec6-3462f431b8cd']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e6aec36f-0f60-4a06-bec6-3462f431b8cd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e6aec36f-0f60-4a06-bec6-3462f431b8cd']['InstallationDir'] = ENV["HOME"]

