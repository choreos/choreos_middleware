##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 89125f57-26bd-4f90-8a42-5c20c8328512 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['89125f57-26bd-4f90-8a42-5c20c8328512']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['89125f57-26bd-4f90-8a42-5c20c8328512']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['89125f57-26bd-4f90-8a42-5c20c8328512']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['89125f57-26bd-4f90-8a42-5c20c8328512']['InstallationDir'] = ENV["HOME"]

