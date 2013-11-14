##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 2d4e3c74-0dcd-4759-847d-2cbdebeb1bc4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['2d4e3c74-0dcd-4759-847d-2cbdebeb1bc4']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['2d4e3c74-0dcd-4759-847d-2cbdebeb1bc4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['2d4e3c74-0dcd-4759-847d-2cbdebeb1bc4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['2d4e3c74-0dcd-4759-847d-2cbdebeb1bc4']['InstallationDir'] = ENV["HOME"]

