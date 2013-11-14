##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 7b9e6998-38e5-4fe7-821e-d83f3e19bbe6 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['7b9e6998-38e5-4fe7-821e-d83f3e19bbe6']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['7b9e6998-38e5-4fe7-821e-d83f3e19bbe6']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['7b9e6998-38e5-4fe7-821e-d83f3e19bbe6']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['7b9e6998-38e5-4fe7-821e-d83f3e19bbe6']['InstallationDir'] = ENV["HOME"]

