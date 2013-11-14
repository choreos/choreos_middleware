##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 77d8c8e1-8cf1-4f61-8da6-3334bfa98602 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['77d8c8e1-8cf1-4f61-8da6-3334bfa98602']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['77d8c8e1-8cf1-4f61-8da6-3334bfa98602']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['77d8c8e1-8cf1-4f61-8da6-3334bfa98602']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['77d8c8e1-8cf1-4f61-8da6-3334bfa98602']['InstallationDir'] = ENV["HOME"]

