##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8de8c865-f8b2-4180-87f9-f85538e345fe must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8de8c865-f8b2-4180-87f9-f85538e345fe']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['8de8c865-f8b2-4180-87f9-f85538e345fe']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8de8c865-f8b2-4180-87f9-f85538e345fe']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8de8c865-f8b2-4180-87f9-f85538e345fe']['InstallationDir'] = ENV["HOME"]

