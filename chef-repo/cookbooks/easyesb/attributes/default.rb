# Easy ESB Server
default['easyesb']['url'] = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-choreos1.0.tar.gz"
default['easyesb']['work_dir'] = ENV['HOME']
default['easyesb']['downloaded_file'] = "#{ENV['HOME']}/easyesb-choreos1.0.tar.gz"
default['easyesb']['executable_jar'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/bin/server.jar"
default['easyesb']['bin_folder'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/bin"
default['easyesb']['jar_name'] = "server.jar"
default['easyesb']['admin_endpoint'] = "http://localhost:8180/services/adminExternalEndpoint"
default['easyesb']['log_file'] = "#{ENV['HOME']}/esb-choreos-distribution-1.0-SNAPSHOT/easyesb.log"

# Easy ESB client
default['easyesb']['cli']['url'] = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-choreos-cli1.0.tar.gz"
default['easyesb']['cli']['work_dir'] = ENV['HOME']
default['easyesb']['cli']['downloaded_file'] = "#{ENV['HOME']}/easyesb-choreos-cli1.0.tar.gz"
default['easyesb']['cli']['executable_jar'] = "#{ENV['HOME']}/easyesb-choreos-cli-1.0-SNAPSHOT-CHOREOS/bin/client.jar"
default['easyesb']['cli']['bin_folder'] = "#{ENV['HOME']}/easyesb-choreos-cli-1.0-SNAPSHOT-CHOREOS/bin"
default['easyesb']['cli']['jar_name'] = "client.jar"

