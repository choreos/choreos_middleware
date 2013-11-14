##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ff2a7e77-2091-4f23-8970-567a9c15bda0 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ff2a7e77-2091-4f23-8970-567a9c15bda0']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['ff2a7e77-2091-4f23-8970-567a9c15bda0']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ff2a7e77-2091-4f23-8970-567a9c15bda0']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ff2a7e77-2091-4f23-8970-567a9c15bda0']['InstallationDir'] = ENV["HOME"]

