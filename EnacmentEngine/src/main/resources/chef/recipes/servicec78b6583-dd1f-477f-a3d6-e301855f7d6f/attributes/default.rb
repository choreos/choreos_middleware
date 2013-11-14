##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c78b6583-dd1f-477f-a3d6-e301855f7d6f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c78b6583-dd1f-477f-a3d6-e301855f7d6f']['WarFile'] = "airline.war"
default['CHOReOSData']['serviceData']['c78b6583-dd1f-477f-a3d6-e301855f7d6f']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c78b6583-dd1f-477f-a3d6-e301855f7d6f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c78b6583-dd1f-477f-a3d6-e301855f7d6f']['InstallationDir'] = ENV["HOME"]

