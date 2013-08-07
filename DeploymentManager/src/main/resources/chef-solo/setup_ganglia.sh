#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

GANGLIA="ganglia"
ip="$THE_IP"

#### Edit JSON
. chef-solo/add_recipe_to_node.sh $GANGLIA

#### Edit ganglia recipe
cd $HOME/chef-solo
sed -i '/\localhost/ s##'"$ip"'#g' node.json