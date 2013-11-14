##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 5efed0bd-f7a6-4de1-abf2-2650c06bcdb5 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['5efed0bd-f7a6-4de1-abf2-2650c06bcdb5']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['5efed0bd-f7a6-4de1-abf2-2650c06bcdb5']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['5efed0bd-f7a6-4de1-abf2-2650c06bcdb5']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['5efed0bd-f7a6-4de1-abf2-2650c06bcdb5']['InstallationDir'] = ENV["HOME"]

