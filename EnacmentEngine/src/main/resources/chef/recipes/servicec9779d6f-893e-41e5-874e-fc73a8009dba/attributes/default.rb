##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of c9779d6f-893e-41e5-874e-fc73a8009dba must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['c9779d6f-893e-41e5-874e-fc73a8009dba']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['c9779d6f-893e-41e5-874e-fc73a8009dba']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['c9779d6f-893e-41e5-874e-fc73a8009dba']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['c9779d6f-893e-41e5-874e-fc73a8009dba']['InstallationDir'] = ENV["HOME"]

