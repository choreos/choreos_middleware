# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

default['cd']['$NAME']['url'] = "$PACKAGE_URL"
default['cd']['$NAME']['downloaded_file'] = "#{ENV['HOME']}/cd-$NAME.tar.gz"
default['cd']['$NAME']['xml'] = "#{ENV['HOME']}/config.xml"
default['cd']['$NAME']['log'] = "#{ENV['HOME']}/cd-$NAME-deployment.log"
default['cd']['$NAME']['work_dir'] = ENV['HOME']

