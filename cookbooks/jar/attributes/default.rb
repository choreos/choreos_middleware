# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

default['CHOReOSData']['serviceData']['$NAME']['PackageURL'] = "$PACKAGE_URL"
default['CHOReOSData']['serviceData']['$NAME']['logFile'] = "/dev/stdout" # needed?
default['CHOReOSData']['serviceData']['$NAME']['InstallationDir'] = ENV["HOME"]

