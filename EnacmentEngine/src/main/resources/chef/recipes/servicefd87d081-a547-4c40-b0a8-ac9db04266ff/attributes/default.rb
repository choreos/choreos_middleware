##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of fd87d081-a547-4c40-b0a8-ac9db04266ff must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['fd87d081-a547-4c40-b0a8-ac9db04266ff']['InstallationDir'] = ENV["HOME"]

