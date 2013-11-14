##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of e825e501-5f10-4a19-966e-4f6d669950ea must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['e825e501-5f10-4a19-966e-4f6d669950ea']['InstallationDir'] = ENV["HOME"]

