# CD Deployment
default['cd']['$NAME']['url'] = "$CD_URL"
default['cd']['$NAME']['work_dir'] = "#{node['easyesb']['cli']['work_dir']}"
default['cd']['$NAME']['downloaded_file'] = "#{node['cd']['$NAME']['work_dir']}/cd.tar.gz"
default['cd']['$NAME']['xml'] = "#{node['cd']['$NAME']['work_dir']}/config.xml"
default['cd']['$NAME']['log'] = "#{node['cd']['$NAME']['work_dir']}/cd-$NAME-deployment.log"
