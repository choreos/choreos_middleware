# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

# Easy ESB Server
default['easyesb']['url'] = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-cd-08.10.13.tar.gz"
default['easyesb']['work_dir'] = ENV['HOME']
default['easyesb']['downloaded_file'] = "#{ENV['HOME']}/easyesb-cd-08.10.13.tar.gz"
default['easyesb']['executable_jar'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/bin/server.jar"
default['easyesb']['bin_folder'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/bin"
default['easyesb']['jar_name'] = "server.jar"
default['easyesb']['admin_endpoint'] = "http://localhost:8180/services/adminExternalEndpoint"
default['easyesb']['log_file'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/easyesb0.log"

# Easy ESB client
default['easyesb']['cli']['url'] = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-cli-08.10.13.tar.gz"
default['easyesb']['cli']['work_dir'] = ENV['HOME']
default['easyesb']['cli']['downloaded_file'] = "#{ENV['HOME']}/easyesb-cli-08.10.13.tar.gz"
default['easyesb']['cli']['executable_jar'] = "#{ENV['HOME']}/easyesb-cli-1.0-SNAPSHOT/bin/client.jar"
default['easyesb']['cli']['bin_folder'] = "#{ENV['HOME']}/easyesb-cli-1.0-SNAPSHOT/bin"
default['easyesb']['cli']['jar_name'] = "client.jar"

