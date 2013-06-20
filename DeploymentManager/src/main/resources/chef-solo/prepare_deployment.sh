#! /bin/bash
# arg $1 -jar or -war flag
# arg $2 package URI
# arg $3 Deployment Manager URI

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

function add_jar_service() {
	cd $HOME/chef-solo
	s_name=`uuidgen`
	cp -ra cookbooks/jar cookbooks/$s_name
	edit_recipe $1 $2 $s_name
}

function install_jar() {
	cd $HOME/chef-solo
	if [ ! -f node.json ];then
		write_jar_json
	fi
	add_jar_service $1 $2
}

case "$1" in
        -war)
                install_war $2 $3 >> /tmp/chef-solo.log  2>&1 
                ;;
        -jar)   
                install_jar $2 $3 >> /tmp/chef-solo.log  2>&1 
                ;;
        *)      
                echo "Usage: prepare_deployment [-jar|-war]"
                ;;
esac
echo $s_name | tr -d '\n'

