##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 6d32f772-c878-4ba2-a964-bb4080496c9d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['6d32f772-c878-4ba2-a964-bb4080496c9d']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['6d32f772-c878-4ba2-a964-bb4080496c9d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['6d32f772-c878-4ba2-a964-bb4080496c9d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['6d32f772-c878-4ba2-a964-bb4080496c9d']['InstallationDir'] = ENV["HOME"]

