##########################################################################
#                                                                        #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                                                                        #
# All ocurrences of d293b4f2-bf5a-45e0-8312-1d23c871d330 must be replaced with the actual service name  #
#            before uploading the recipe to the chef-server              #
#                                                                        #
##########################################################################


# Name of the deployed service 
default['CHOReOSData']['serviceData']['d293b4f2-bf5a-45e0-8312-1d23c871d330']['WarFile'] = "myServletWAR.war"
default['CHOReOSData']['serviceData']['d293b4f2-bf5a-45e0-8312-1d23c871d330']['PackageURL'] = "https://github.com/downloads/choreos/choreos_middleware/myServletWAR.war"

# Set the default log filewar_depl
default['CHOReOSData']['serviceData']['d293b4f2-bf5a-45e0-8312-1d23c871d330']['logFile'] = "/dev/stdout"

# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['d293b4f2-bf5a-45e0-8312-1d23c871d330']['InstallationDir'] = ENV["HOME"]

