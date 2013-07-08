#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

# arg $1 cookbook name

function edit_json() {
    cd $HOME/chef-solo
    echo 'previous node.json:'
    cat node.json 
    sed -i -r 's/.*\"run_list\" : \[/\t\"run_list\" : \[\n\t\t\"recipe\['"$1"']\",/' node.json
    echo 'node.json edited:'
    cat node.json
}

edit_json $1 >> /tmp/chef-solo-prepare.log  2>&1
