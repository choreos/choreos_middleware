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

ruby_block "remove-service2381d610-8f1f-4b1a-b9c6-74a68f55c73b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service2381d610-8f1f-4b1a-b9c6-74a68f55c73b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2381d610-8f1f-4b1a-b9c6-74a68f55c73b.war]", :immediately
end

ruby_block "remove-service2381d610-8f1f-4b1a-b9c6-74a68f55c73b" do
  block do
  	node.run_list.remove("recipe[service2381d610-8f1f-4b1a-b9c6-74a68f55c73b::war]")
  end
  only_if { node.run_list.include?("recipe[service2381d610-8f1f-4b1a-b9c6-74a68f55c73b::war]") }
end

ruby_block "remove-deactivate-service2381d610-8f1f-4b1a-b9c6-74a68f55c73b" do
  block do
    node.run_list.remove("recipe[deactivate-service2381d610-8f1f-4b1a-b9c6-74a68f55c73b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2381d610-8f1f-4b1a-b9c6-74a68f55c73b]") }
end
