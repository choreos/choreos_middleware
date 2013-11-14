#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7ab73ca3-e7d7-4245-b7e9-d5bff01d9883.war]", :immediately
end

ruby_block "remove-service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883" do
  block do
  	node.run_list.remove("recipe[service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883::war]")
  end
  only_if { node.run_list.include?("recipe[service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883::war]") }
end

ruby_block "remove-deactivate-service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883" do
  block do
    node.run_list.remove("recipe[deactivate-service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7ab73ca3-e7d7-4245-b7e9-d5bff01d9883]") }
end
