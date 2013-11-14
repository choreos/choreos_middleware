##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7a028df1-bdf6-46ae-885e-f58a5b46faea must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7a028df1-bdf6-46ae-885e-f58a5b46faea']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['7a028df1-bdf6-46ae-885e-f58a5b46faea']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7a028df1-bdf6-46ae-885e-f58a5b46faea']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7a028df1-bdf6-46ae-885e-f58a5b46faea']['InstallationDir'] = ENV["HOME"]

