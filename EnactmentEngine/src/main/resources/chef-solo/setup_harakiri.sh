#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

HARAKIRI="harakiri"
dmurl="$THE_URL"
nodeid="$THE_ID"

#### Edit JSON
. chef-solo/add_recipe_to_node.sh $HARAKIRI

#### Edit harakiri recipe
cd $HOME/chef-solo/cookbooks/harakiri
sed -i '/\$DM_URL/ s##'"$dmurl"'#g' attributes/default.rb
sed -i '/\$NODE_ID/ s##'"$nodeid"'#g' attributes/default.rb
