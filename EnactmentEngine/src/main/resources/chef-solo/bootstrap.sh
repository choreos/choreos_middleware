#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/.

COOKBOOKS_URL=$THE_COOKBOOKS_URL

function install_chef_solo() {
	if which chef-solo >/dev/null; then
		echo "Chef-solo already installed."
	else
		echo "Chef-solo not installed (going to install it)."
		# install lsb-core to get release with lsb_release
		sudo apt-get update
		#sudo apt-get -q -y install lsb-core 
	
		# add opscode apt repository and opscode key
		echo "deb http://apt.opscode.com/ `lsb_release -cs`-0.10 main" | sudo tee /etc/apt/sources.list.d/opscode.list
		gpg --fetch-key http://apt.opscode.com/packages@opscode.com.gpg.key
		gpg --export packages@opscode.com | sudo tee /etc/apt/trusted.gpg.d/opscode-keyring.gpg > /dev/null
	
		# install chef quietly and with no chef installation prompt
		sudo apt-get update
		echo "chef chef/chef_server_url string none" | sudo debconf-set-selections && sudo apt-get --no-install-recommends install chef -y
	
		# remove chef-client from run level
		sudo update-rc.d -f chef-client remove
		sudo service chef-client stop
	fi
}

function prepare_node() {
	if [ ! -d $HOME/chef-solo ]; then
        mkdir -p $HOME/chef-solo/cookbooks
	
		# create cookbooks repo
		cd $HOME/chef-solo
	    wget -O - $COOKBOOKS_URL | tar xzf -

		#create solo.rb file
		cp /usr/share/chef/solo.rb .

        # configuring cookbook_path in solo.rb
		chefrepo=$HOME/chef-solo/cookbooks 
		sed -i "s#^cookbook_path.*\[.*\]#cookbook_path\t\[\"$chefrepo\"\]#g" solo.rb

        # configuring json_attribs in solo.rb
		attribs=$HOME/chef-solo/node.json
		sed -i -r "s@(\#)?json_attribs.*\".*\"@json_attribs \"$attribs\"@g" solo.rb
		# remove duplicate lines (http://sed.sourceforge.net/sed1line.txt)
		# but i do not know how it works, :-)
		sed '$!N; /^\(.*\)\n\1$/!P; D' -i solo.rb

	fi
}

install_chef_solo > /tmp/chef-solo-bootstrap.log 2>&1
prepare_node >> /tmp/chef-solo-bootstrap.log 2>&1



