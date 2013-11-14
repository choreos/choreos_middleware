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

ruby_block "remove-service18303ce1-d313-4605-b4c2-050865d92857" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service18303ce1-d313-4605-b4c2-050865d92857::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/18303ce1-d313-4605-b4c2-050865d92857.war]", :immediately
end

ruby_block "remove-service18303ce1-d313-4605-b4c2-050865d92857" do
  block do
  	node.run_list.remove("recipe[service18303ce1-d313-4605-b4c2-050865d92857::war]")
  end
  only_if { node.run_list.include?("recipe[service18303ce1-d313-4605-b4c2-050865d92857::war]") }
end

ruby_block "remove-deactivate-service18303ce1-d313-4605-b4c2-050865d92857" do
  block do
    node.run_list.remove("recipe[deactivate-service18303ce1-d313-4605-b4c2-050865d92857]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service18303ce1-d313-4605-b4c2-050865d92857]") }
end
