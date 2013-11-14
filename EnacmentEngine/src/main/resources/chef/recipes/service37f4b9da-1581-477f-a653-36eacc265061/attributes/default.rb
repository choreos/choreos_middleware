##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 37f4b9da-1581-477f-a653-36eacc265061 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['37f4b9da-1581-477f-a653-36eacc265061']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['37f4b9da-1581-477f-a653-36eacc265061']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['37f4b9da-1581-477f-a653-36eacc265061']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['37f4b9da-1581-477f-a653-36eacc265061']['InstallationDir'] = ENV["HOME"]

