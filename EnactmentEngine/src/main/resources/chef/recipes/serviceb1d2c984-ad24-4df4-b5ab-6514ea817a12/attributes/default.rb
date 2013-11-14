##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b1d2c984-ad24-4df4-b5ab-6514ea817a12 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b1d2c984-ad24-4df4-b5ab-6514ea817a12']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['b1d2c984-ad24-4df4-b5ab-6514ea817a12']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b1d2c984-ad24-4df4-b5ab-6514ea817a12']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b1d2c984-ad24-4df4-b5ab-6514ea817a12']['InstallationDir'] = ENV["HOME"]

