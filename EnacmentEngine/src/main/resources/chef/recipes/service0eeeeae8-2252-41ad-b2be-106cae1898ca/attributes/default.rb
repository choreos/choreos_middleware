##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 0eeeeae8-2252-41ad-b2be-106cae1898ca must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['0eeeeae8-2252-41ad-b2be-106cae1898ca']['InstallationDir'] = ENV["HOME"]

