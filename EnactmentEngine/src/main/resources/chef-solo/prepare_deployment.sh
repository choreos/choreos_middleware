#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

# arg $1 package URL
# arg $2 cookbook template name

function copy_template() {
    cd $HOME/chef-solo
    cp -ra "cookbooks/$2" cookbooks/$3
    echo "$2 template copied to folder cookbooks/$3"
}

function edit_recipe() {
    # edit recipe replacing: $NAME, $PACKAGE_URL
    cd cookbooks/$3
    sed -i '/\$PACKAGE_URL/ s##'"$1"'#g' attributes/default.rb
    sed -i '/\$NAME/ s##'"$3"'#g' attributes/default.rb
    sed -i '/\$NAME/ s##'"$3"'#g' recipes/default.rb	
    sed -i '/\$NAME/ s##'"$3"'#g' recipes/remove.rb
    echo "Cookbook $3 edited"
}

function edit_json() {
    cd $HOME/chef-solo
    . add_recipe_to_node.sh $3
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

