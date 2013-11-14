##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 8941eddf-d5a4-43b9-a290-93271f8aa88f must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['8941eddf-d5a4-43b9-a290-93271f8aa88f']['InstallationDir'] = ENV["HOME"]

