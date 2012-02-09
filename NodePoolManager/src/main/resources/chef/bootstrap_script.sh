sudo bash -c '
if [ ! -f /usr/bin/chef-client ]; then
  apt-get update
  apt-get install -y ruby ruby1.8-dev build-essential wget libruby-extras libruby1.8-extras
  cd /tmp
  wget http://production.cf.rubygems.org/rubygems/rubygems-1.3.7.tgz
  tar zxf rubygems-1.3.7.tgz
  cd rubygems-1.3.7
  ruby setup.rb --no-format-executable
fi

gem install ohai --no-rdoc --no-ri --verbose
gem install chef --no-rdoc --no-ri --verbose --version 0.10.2

mkdir -p /etc/chef

(
cat <<'EOP'
$pkey
EOP
) > /tmp/validation.pem
awk NF /tmp/validation.pem > /etc/chef/validation.pem
rm /tmp/validation.pem

(
cat <<'EOP'
log_level        :info
log_location     STDOUT
chef_server_url  "$chefserver"
validation_client_name "$validator"
# Using default node name (fqdn)

EOP
) > /etc/chef/client.rb

(
cat <<'EOP'
//{"run_list":["role[petals]"]}
EOP
) > /etc/chef/first-boot.json

/usr/bin/chef-client -j /etc/chef/first-boot.json -E _default'