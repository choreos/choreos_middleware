##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7994b326-ec00-4d71-87e5-0dcc614cabe3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7994b326-ec00-4d71-87e5-0dcc614cabe3']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['7994b326-ec00-4d71-87e5-0dcc614cabe3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7994b326-ec00-4d71-87e5-0dcc614cabe3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7994b326-ec00-4d71-87e5-0dcc614cabe3']['InstallationDir'] = ENV["HOME"]

