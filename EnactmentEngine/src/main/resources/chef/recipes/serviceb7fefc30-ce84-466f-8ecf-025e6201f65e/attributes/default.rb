##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b7fefc30-ce84-466f-8ecf-025e6201f65e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b7fefc30-ce84-466f-8ecf-025e6201f65e']['WarFile'] = ""
default['CHOReOSData']['serviceData']['b7fefc30-ce84-466f-8ecf-025e6201f65e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b7fefc30-ce84-466f-8ecf-025e6201f65e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b7fefc30-ce84-466f-8ecf-025e6201f65e']['InstallationDir'] = ENV["HOME"]

