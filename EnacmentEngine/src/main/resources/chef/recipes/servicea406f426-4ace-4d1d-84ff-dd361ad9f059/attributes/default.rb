##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a406f426-4ace-4d1d-84ff-dd361ad9f059 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a406f426-4ace-4d1d-84ff-dd361ad9f059']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['a406f426-4ace-4d1d-84ff-dd361ad9f059']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a406f426-4ace-4d1d-84ff-dd361ad9f059']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a406f426-4ace-4d1d-84ff-dd361ad9f059']['InstallationDir'] = ENV["HOME"]

