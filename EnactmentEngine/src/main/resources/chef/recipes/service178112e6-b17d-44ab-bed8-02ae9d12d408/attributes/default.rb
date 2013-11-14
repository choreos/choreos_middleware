##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 178112e6-b17d-44ab-bed8-02ae9d12d408 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['178112e6-b17d-44ab-bed8-02ae9d12d408']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['178112e6-b17d-44ab-bed8-02ae9d12d408']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['178112e6-b17d-44ab-bed8-02ae9d12d408']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['178112e6-b17d-44ab-bed8-02ae9d12d408']['InstallationDir'] = ENV["HOME"]

