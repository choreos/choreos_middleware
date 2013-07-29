#! /bin/bash
# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

# arg $1 instance_uuid

function edit_json() {
    cd $HOME/chef-solo
    echo 'previous node.json:'
    cat node.json
    sed -i -r 's/'"$1"'/'"$1"'::remove/g' node.json
    echo 'node.json edited:'
    cat node.json
}

function prepare() {
    echo '==========================='
    echo "Preparing undeployment of $1 at `date`"
    edit_json $1
    echo "Instance undeployment for $1 is prepared"
}

prepare $1 >> /tmp/chef-solo-prepare-undeplyment.log  2>&1

