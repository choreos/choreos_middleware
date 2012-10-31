# CD Deployment
default['cd']['$NAME']['url'] = "$CD_URL"
default['cd']['$NAME']['work_dir'] = "#{node['easyesb']['cli']['work_dir']}"
default['cd']['$NAME']['downloaded_file'] = "#{node['cd']['$NAME']['work_dir']}/cd.tar.gz"
default['cd']['$NAME']['xml'] = "#{node['cd']['$NAME']['work_dir']}/config.xml"
# TODO em vez de usar HOME fazer em funcao do work_dir do easyesb-client

