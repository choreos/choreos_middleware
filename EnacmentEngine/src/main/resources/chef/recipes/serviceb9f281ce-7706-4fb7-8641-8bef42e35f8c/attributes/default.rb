##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b9f281ce-7706-4fb7-8641-8bef42e35f8c must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b9f281ce-7706-4fb7-8641-8bef42e35f8c']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['b9f281ce-7706-4fb7-8641-8bef42e35f8c']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b9f281ce-7706-4fb7-8641-8bef42e35f8c']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b9f281ce-7706-4fb7-8641-8bef42e35f8c']['InstallationDir'] = ENV["HOME"]

