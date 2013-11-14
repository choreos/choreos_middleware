##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of ac9a6319-a9c2-4a1a-8836-33fe854481b4 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['WarFile'] = ""
default['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['ac9a6319-a9c2-4a1a-8836-33fe854481b4']['InstallationDir'] = ENV["HOME"]

