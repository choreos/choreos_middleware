# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

#file "#{node['tomcat']['webapp_dir']}/$NAME.war" do
#	action :delete
#end

#bash "edit_json" do
#    cwd "#{ENV['HOME']}"
#    code "sed -i '/$NAME::remove/d' ./chef-solo/node.json"
#end

#bash "remove_$NAME_recipe" do
#    cwd "#{ENV['HOME']}"
#    code "rm -rf chef-solo/cookbooks/$NAME"
#end

template "#{ENV['HOME']}/schedule-removal.sh" do
    source "schedule-removal.erb"
    owner "ubuntu"
    group "ubuntu"
    mode 0755
end

execute "schedule-removal" do
    command "echo \"bash #{ENV['HOME']}/schedule-removal.sh $NAME \" | at now + 10 min"
    action :run
end

