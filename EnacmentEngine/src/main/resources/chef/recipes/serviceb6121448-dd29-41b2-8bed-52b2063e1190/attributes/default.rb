##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of b6121448-dd29-41b2-8bed-52b2063e1190 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['b6121448-dd29-41b2-8bed-52b2063e1190']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['b6121448-dd29-41b2-8bed-52b2063e1190']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['b6121448-dd29-41b2-8bed-52b2063e1190']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['b6121448-dd29-41b2-8bed-52b2063e1190']['InstallationDir'] = ENV["HOME"]

