##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of a4e701c8-6bde-4471-bf5b-dee4e00cfb1e must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['a4e701c8-6bde-4471-bf5b-dee4e00cfb1e']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['a4e701c8-6bde-4471-bf5b-dee4e00cfb1e']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['a4e701c8-6bde-4471-bf5b-dee4e00cfb1e']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['a4e701c8-6bde-4471-bf5b-dee4e00cfb1e']['InstallationDir'] = ENV["HOME"]

