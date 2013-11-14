##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e22b8b22-79c2-457a-8e37-575e7e535fec must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['WarFile'] = "travelagency.war"
default['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['PackageURL'] = "http://valinhos.ime.usp.br:54080/enact_test/middleware/travelagency.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e22b8b22-79c2-457a-8e37-575e7e535fec']['InstallationDir'] = ENV["HOME"]

