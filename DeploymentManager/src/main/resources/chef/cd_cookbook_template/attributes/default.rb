# CD Deployment
default['cd']['$NAME']['url'] = "$CD_URL"
default['cd']['$NAME']['downloaded_file'] = "#{ENV['HOME']}/cd-$NAME.tar.gz"
default['cd']['$NAME']['xml'] = "#{ENV['HOME']}/config.xml"
default['cd']['$NAME']['log'] = "#{ENV['HOME']}/cd-$NAME-deployment.log"
default['cd']['$NAME']['work_dir'] = ENV['HOME']

