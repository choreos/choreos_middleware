##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 3ce54e12-eded-42e7-a489-d4aa941beb95 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['3ce54e12-eded-42e7-a489-d4aa941beb95']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['3ce54e12-eded-42e7-a489-d4aa941beb95']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['3ce54e12-eded-42e7-a489-d4aa941beb95']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['3ce54e12-eded-42e7-a489-d4aa941beb95']['InstallationDir'] = ENV["HOME"]

