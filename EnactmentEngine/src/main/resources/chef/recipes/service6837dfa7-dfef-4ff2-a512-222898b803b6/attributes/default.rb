##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6837dfa7-dfef-4ff2-a512-222898b803b6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6837dfa7-dfef-4ff2-a512-222898b803b6']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['6837dfa7-dfef-4ff2-a512-222898b803b6']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6837dfa7-dfef-4ff2-a512-222898b803b6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6837dfa7-dfef-4ff2-a512-222898b803b6']['InstallationDir'] = ENV["HOME"]

