##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of aacc49f1-dd4c-456d-a10d-cde4be062dca must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['aacc49f1-dd4c-456d-a10d-cde4be062dca']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['aacc49f1-dd4c-456d-a10d-cde4be062dca']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['aacc49f1-dd4c-456d-a10d-cde4be062dca']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['aacc49f1-dd4c-456d-a10d-cde4be062dca']['InstallationDir'] = ENV["HOME"]

