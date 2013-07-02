#! /bin/bash
# arg $1 package URL
# arg $2 cookbook template name

function copy_template() {
    cd $HOME/chef-solo
    cp -ra "cookbooks/$2" cookbooks/$3
    echo "$2 template copied to folder cookbooks/$3"
}

function edit_recipe() {
    # edit recipe ($NAME, $PackageURL)
    cd cookbooks/$3
    sed -i '/\$PackageURL/ s##'"$1"'#g' attributes/default.rb
    sed -i '/\$NAME/ s##'"$3"'#g' attributes/default.rb
    sed -i '/\$NAME/ s##'"$3"'#g' recipes/default.rb
    echo "Cookbook $3 edited"
}

function edit_json() {
    cd $HOME/chef-solo
    echo 'previous node.json:'
    cat node.json
    sed -i -r 's/.*\"run_list\" : \[/\t\"run_list\" : \[\n\t\t\"recipe\['"$3"']\",/' node.json
    echo 'node.json edited:'
    cat node.json
}

function prepare() {
    instance_uuid=`uuidgen`
    echo '==========================='
    echo "Preparing $2 deployment of $instance_uuid at `date`"
    copy_template $1 $2 $instance_uuid
    edit_recipe $1 $2 $instance_uuid
    edit_json $1 $2 $instance_uuid
    echo "Instance $instance_uuid is prepared"
}

prepare $1 $2 >> /tmp/chef-solo-prepare.log  2>&1 
echo $instance_uuid | tr -d '\n'

