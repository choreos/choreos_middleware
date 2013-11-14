##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d']['WarFile'] = "airline.jar"
default['CHOReOSData']['serviceData']['0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d']['PackageURL'] = "http://choreos.eu/services/airline.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0b1c3dc4-7c0b-4e95-9f45-a91b292fb31d']['InstallationDir'] = ENV["HOME"]

