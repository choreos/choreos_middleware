##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 39225113-2688-439b-986d-167c28f60d66 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['39225113-2688-439b-986d-167c28f60d66']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['39225113-2688-439b-986d-167c28f60d66']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['39225113-2688-439b-986d-167c28f60d66']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['39225113-2688-439b-986d-167c28f60d66']['InstallationDir'] = ENV["HOME"]

