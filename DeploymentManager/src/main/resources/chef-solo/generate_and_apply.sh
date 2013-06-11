#! /bin/bash


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
		echo "chef chef/chef_server_url string none" | sudo debconf-set-selections && sudo apt-get install chef -y
	
		# remove chef-client from run level
		sudo update-rc.d -f chef-client remove
		sudo service chef-client stop
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
	]
}' >> node.json

}

function add_war_service() {
        cd $HOME/chef-solo
        s_name=`uuidgen`
        cp -ra cookbooks/war cookbooks/$s_name
	edit_recipe $1 $2 $s_name
}  

function install_war() {
        prepare_node
	cd $HOME/chef-solo
	if [ ! -f node.json ]; then
		write_war_json
	fi
	add_war_service $1 $2
}

function write_jar_json() {

echo '{
        "run_list" : [
                "recipe[apt]",
                "recipe[java]"
        ]
}' >> node.json

}

function edit_recipe() {
	 # edit recipe ($PackageURL , $NAME, $DeploymentManagerURL, $WARFILE)
        cd cookbooks/$3

        sed -i '/\$PackageURL/ s##'"$1"'#g' attributes/default.rb
        sed -i '/\$NAME/ s##'"$3"'#g' attributes/default.rb
        sed -i '/\$DeploymentManagerURL/ s##'"$2"'#g' attributes/default.rb
        sed -i '/\$WARFILE/ s##'"$3"'#g' attributes/default.rb

        sed -i '/\$NAME/ s##'"$3"'#g' recipes/default.rb

        cd $HOME/chef-solo
        # edit node.json (add new service recipe)
        sed -i -r -e 's/.*\"recipe\[apt\]\",/\t\t\"recipe[apt]\",\n\t\t\"recipe\['"$3"']\",/g' node.json
}

# AIRLINE_JAR=http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline-service.jar
# AIRLINE_WAR=http://valinhos.ime.usp.br:54080/enact_test/v2-2/airline.war
function add_jar_service() {
	cd $HOME/chef-solo
	s_name=`uuidgen`
	cp -ra cookbooks/jar cookbooks/$s_name
	edit_recipe $1 $2 $s_name
}

function install_jar() {
        prepare_node
	cd $HOME/chef-solo
	if [ ! -f node.json ];then
		write_jar_json
	fi
	add_jar_service $1 $2
}

case "$1" in
        -war)
                install_war $2 $3 > /tmp/chef-solo.log  2>&1 
                ;;
        -jar)   
                install_jar $2 $3 > /tmp/chef-solo.log  2>&1 
                ;;
        *)      
                echo "Usage: generate_and_apply [-jar|-war]"
                ;;
esac
echo $s_name
#sudo chef-solo -c $HOME/chef-solo/solo.rb

