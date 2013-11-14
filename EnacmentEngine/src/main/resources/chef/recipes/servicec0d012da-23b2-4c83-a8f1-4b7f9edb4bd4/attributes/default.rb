##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c0d012da-23b2-4c83-a8f1-4b7f9edb4bd4']['InstallationDir'] = ENV["HOME"]

