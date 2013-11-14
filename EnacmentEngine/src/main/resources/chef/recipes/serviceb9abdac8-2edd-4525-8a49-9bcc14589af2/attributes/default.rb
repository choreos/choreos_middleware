##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b9abdac8-2edd-4525-8a49-9bcc14589af2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b9abdac8-2edd-4525-8a49-9bcc14589af2']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['b9abdac8-2edd-4525-8a49-9bcc14589af2']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b9abdac8-2edd-4525-8a49-9bcc14589af2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b9abdac8-2edd-4525-8a49-9bcc14589af2']['InstallationDir'] = ENV["HOME"]

