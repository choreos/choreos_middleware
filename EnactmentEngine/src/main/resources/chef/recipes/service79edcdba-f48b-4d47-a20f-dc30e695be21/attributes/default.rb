##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 79edcdba-f48b-4d47-a20f-dc30e695be21 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['79edcdba-f48b-4d47-a20f-dc30e695be21']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['79edcdba-f48b-4d47-a20f-dc30e695be21']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['79edcdba-f48b-4d47-a20f-dc30e695be21']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['79edcdba-f48b-4d47-a20f-dc30e695be21']['InstallationDir'] = ENV["HOME"]

