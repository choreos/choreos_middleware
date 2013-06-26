
# NOTE: this is a template file. $variables must be properly replaced.

# Name of the deployed service 
default['CHOReOSData']['serviceData']['$NAME']['PackageURL'] = "$PackageURL"
# Set the destination folder for JAR files
default['CHOReOSData']['serviceData']['$NAME']['InstallationDir'] = ENV["HOME"]

