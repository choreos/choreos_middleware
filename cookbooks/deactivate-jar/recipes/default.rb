# This Source Code Form is subject to the terms of the Mozilla Public
# License, v. 2.0. If a copy of the MPL was not distributed with this
# file, You can obtain one at http://mozilla.org/MPL/2.0/. 

ruby_block "disable-service$NAME" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service$NAME::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['$NAME']['InstallationDir']}/service-$NAME.jar]", :immediately
end

ruby_block "remove-service$NAME" do
  block do
  	node.run_list.remove("recipe[service$NAME::jar]")
  end
  only_if { node.run_list.include?("recipe[service$NAME::jar]") }
end

ruby_block "remove-deactivate-service$NAME" do
  block do
    node.run_list.remove("recipe[deactivate-service$NAME]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service$NAME]") }
end
