##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 80b28b07-aff7-4f61-8634-309547a4484b must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['80b28b07-aff7-4f61-8634-309547a4484b']['WarFile'] = "travel-agency-service.jar"
default['CHOReOSData']['serviceData']['80b28b07-aff7-4f61-8634-309547a4484b']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['80b28b07-aff7-4f61-8634-309547a4484b']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['80b28b07-aff7-4f61-8634-309547a4484b']['InstallationDir'] = ENV["HOME"]

