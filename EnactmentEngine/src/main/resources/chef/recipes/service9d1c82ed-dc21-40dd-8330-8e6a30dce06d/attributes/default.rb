##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 9d1c82ed-dc21-40dd-8330-8e6a30dce06d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['9d1c82ed-dc21-40dd-8330-8e6a30dce06d']['WarFile'] = "airline-service.jar"
default['CHOReOSData']['serviceData']['9d1c82ed-dc21-40dd-8330-8e6a30dce06d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['9d1c82ed-dc21-40dd-8330-8e6a30dce06d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['9d1c82ed-dc21-40dd-8330-8e6a30dce06d']['InstallationDir'] = ENV["HOME"]

