##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2cb3a033-b2a4-4e42-8386-0e1ebc398c1a must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2cb3a033-b2a4-4e42-8386-0e1ebc398c1a']['InstallationDir'] = ENV["HOME"]

