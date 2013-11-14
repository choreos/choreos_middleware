##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of f0d9118a-63b4-40d2-abc7-5101c12663b3 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['f0d9118a-63b4-40d2-abc7-5101c12663b3']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['f0d9118a-63b4-40d2-abc7-5101c12663b3']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['f0d9118a-63b4-40d2-abc7-5101c12663b3']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['f0d9118a-63b4-40d2-abc7-5101c12663b3']['InstallationDir'] = ENV["HOME"]

