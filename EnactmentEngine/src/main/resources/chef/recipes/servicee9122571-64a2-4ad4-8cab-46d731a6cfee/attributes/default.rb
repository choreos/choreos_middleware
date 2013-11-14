##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e9122571-64a2-4ad4-8cab-46d731a6cfee must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e9122571-64a2-4ad4-8cab-46d731a6cfee']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['e9122571-64a2-4ad4-8cab-46d731a6cfee']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e9122571-64a2-4ad4-8cab-46d731a6cfee']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e9122571-64a2-4ad4-8cab-46d731a6cfee']['InstallationDir'] = ENV["HOME"]

