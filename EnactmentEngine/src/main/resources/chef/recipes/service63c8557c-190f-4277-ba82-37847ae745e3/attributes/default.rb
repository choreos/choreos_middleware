##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 63c8557c-190f-4277-ba82-37847ae745e3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['63c8557c-190f-4277-ba82-37847ae745e3']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['63c8557c-190f-4277-ba82-37847ae745e3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['63c8557c-190f-4277-ba82-37847ae745e3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['63c8557c-190f-4277-ba82-37847ae745e3']['InstallationDir'] = ENV["HOME"]

