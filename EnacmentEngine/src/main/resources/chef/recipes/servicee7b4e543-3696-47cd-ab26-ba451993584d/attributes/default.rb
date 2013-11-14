##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e7b4e543-3696-47cd-ab26-ba451993584d must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e7b4e543-3696-47cd-ab26-ba451993584d']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['e7b4e543-3696-47cd-ab26-ba451993584d']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e7b4e543-3696-47cd-ab26-ba451993584d']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e7b4e543-3696-47cd-ab26-ba451993584d']['InstallationDir'] = ENV["HOME"]

