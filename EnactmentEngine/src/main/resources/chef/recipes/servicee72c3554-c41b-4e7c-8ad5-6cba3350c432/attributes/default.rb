##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e72c3554-c41b-4e7c-8ad5-6cba3350c432 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e72c3554-c41b-4e7c-8ad5-6cba3350c432']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e72c3554-c41b-4e7c-8ad5-6cba3350c432']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e72c3554-c41b-4e7c-8ad5-6cba3350c432']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e72c3554-c41b-4e7c-8ad5-6cba3350c432']['InstallationDir'] = ENV["HOME"]

