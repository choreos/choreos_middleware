##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7dbbc6a9-4492-4633-80c8-ec4ef81f50dd must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7dbbc6a9-4492-4633-80c8-ec4ef81f50dd']['InstallationDir'] = ENV["HOME"]

