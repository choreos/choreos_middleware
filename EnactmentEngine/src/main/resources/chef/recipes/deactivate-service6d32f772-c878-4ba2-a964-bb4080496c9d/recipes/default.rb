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

ruby_block "remove-service6d32f772-c878-4ba2-a964-bb4080496c9d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6d32f772-c878-4ba2-a964-bb4080496c9d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/6d32f772-c878-4ba2-a964-bb4080496c9d.war]", :immediately
end

ruby_block "remove-service6d32f772-c878-4ba2-a964-bb4080496c9d" do
  block do
  	node.run_list.remove("recipe[service6d32f772-c878-4ba2-a964-bb4080496c9d::war]")
  end
  only_if { node.run_list.include?("recipe[service6d32f772-c878-4ba2-a964-bb4080496c9d::war]") }
end

ruby_block "remove-deactivate-service6d32f772-c878-4ba2-a964-bb4080496c9d" do
  block do
    node.run_list.remove("recipe[deactivate-service6d32f772-c878-4ba2-a964-bb4080496c9d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6d32f772-c878-4ba2-a964-bb4080496c9d]") }
end
