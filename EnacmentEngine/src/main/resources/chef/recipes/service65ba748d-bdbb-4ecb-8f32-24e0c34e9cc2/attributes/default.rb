##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of 65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2']['InstallationDir'] = ENV["HOME"]

