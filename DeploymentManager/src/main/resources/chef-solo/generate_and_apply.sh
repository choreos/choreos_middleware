#! /bin/bash

function install_chef_solo() {
	if which chef-solo >/dev/null; then
		echo "Chef-solo already installed."
	else
		echo "Chef-solo not installed (going to install it)."
		# install lsb-core to get release with lsb_release
		sudo apt-get update
		sudo apt-get -q -y install lsb-core 
	
		# add opscode apt repository and opscode key
		echo "deb http://apt.opscode.com/ `lsb_release -cs`-0.10 main" | sudo tee /etc/apt/sources.list.d/opscode.list
		gpg --fetch-key http://apt.opscode.com/packages@opscode.com.gpg.key
		gpg --export packages@opscode.com | sudo tee /etc/apt/trusted.gpg.d/opscode-keyring.gpg > /dev/null
	
		# install chef quietly and with no chef installation prompt
		sudo apt-get update
		echo "chef chef/chef_server_url string none" | sudo debconf-set-selections && sudo apt-get install chef -y
	
		# remove chef-client from run level
		sudo update-rc.d -f chef-client remove
	fi
}

function prepare_node() {
	install_chef_solo

	if [ ! -d $HOME/chef-solo ]; then
                mkdir -p $HOME/chef-solo/cookbooks
	
		# create cookbooks repo
		cd $HOME/chef-solo
	        wget -O - http://valinhos.ime.usp.br:54080/choreos/chef-repo.tar.gz | tar xzf -
		mv chef-repo/* cookbooks
		rm -rf chef-repo

		#create solo.rb file
		cp /usr/share/chef/solo.rb .
		chefrepo=$HOME/chef-solo/cookbooks 
		sed -i "s#^cookbook_path.*\[.*\]#cookbook_path\t\[\"$chefrepo\"\]#g" solo.rb

		attribs=$HOME/chef-solo/node.json
		sed -i -r "s@(\#)?json_attribs.*\".*\"@json_attribs \"$attribs\"@g" solo.rb
		# remove duplicate lines (http://sed.sourceforge.net/sed1line.txt)
		# but i do not know how it works, :-)
		sed '$!N; /^\(.*\)\n\1$/!P; D' -i solo.rb
	fi
}

function write_war_json() {

echo '{
	"run_list" : [
		"recipe[apt]",
		"recipe[java]",
		"recipe[tomcat]"
}' >> node.json

}

function install_war() {
        prepare_node
	cd $HOME/chef-solo
	if [ -f node.json ]; then
		# add service recipe json entry
		echo "not implemented"	
	else
		# create json for first service
		write_war_json
	fi
}

function write_jar_json() {

echo '{
        "run_list" : [
                "recipe[apt]",
                "recipe[java]"
        ]
}' >> node.json

}

function install_jar() {
        prepare_node
	cd $HOME/chef-solo
	if [ -f node.json ]; then
		# add service recipe json entry
		echo "not implemented"
	else
		# create json for first service
		write_jar_json
	fi
}

case "$1" in
        -war)
                install_war
                ;;
        -jar)   
                install_jar
                ;;
        *)      
                echo "Usage: generate_and_apply [-jar|-war]"
                ;;
esac
sudo chef-solo -c $HOME/chef-solo/solo.rb

