##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 78681bda-3abe-4aa5-b10e-64dd3aed4f03 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['78681bda-3abe-4aa5-b10e-64dd3aed4f03']['WarFile'] = ""
default['CHOReOSData']['serviceData']['78681bda-3abe-4aa5-b10e-64dd3aed4f03']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['78681bda-3abe-4aa5-b10e-64dd3aed4f03']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['78681bda-3abe-4aa5-b10e-64dd3aed4f03']['InstallationDir'] = ENV["HOME"]

