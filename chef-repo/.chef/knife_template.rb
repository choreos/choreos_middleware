current_dir = File.dirname(__FILE__)

log_level                :info
log_location             STDOUT
node_name                'USERNAME'
client_key               "#{current_dir}/client_USERNAME_aguia.pem"
validation_client_name   'chef-validator'
validation_key           "#{current_dir}/validation_aguia.pem"
chef_server_url          'http://aguia1.ime.usp.br:4000'
cache_type               'BasicFile'
cache_options( :path => "#{current_dir}/checksums" )
