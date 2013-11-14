##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 28ff0894-bdd4-41c0-9f5b-3efbfc4af23f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['28ff0894-bdd4-41c0-9f5b-3efbfc4af23f']['InstallationDir'] = ENV["HOME"]

