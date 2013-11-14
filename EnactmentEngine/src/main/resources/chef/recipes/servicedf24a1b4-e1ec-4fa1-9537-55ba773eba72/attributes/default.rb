##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of df24a1b4-e1ec-4fa1-9537-55ba773eba72 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['df24a1b4-e1ec-4fa1-9537-55ba773eba72']['WarFile'] = ""
default['CHOReOSData']['serviceData']['df24a1b4-e1ec-4fa1-9537-55ba773eba72']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['df24a1b4-e1ec-4fa1-9537-55ba773eba72']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['df24a1b4-e1ec-4fa1-9537-55ba773eba72']['InstallationDir'] = ENV["HOME"]

