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

ruby_block "remove-service95edb963-c2b5-469d-b586-38d5ad8f7710" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service95edb963-c2b5-469d-b586-38d5ad8f7710::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/95edb963-c2b5-469d-b586-38d5ad8f7710.war]", :immediately
end

ruby_block "remove-service95edb963-c2b5-469d-b586-38d5ad8f7710" do
  block do
  	node.run_list.remove("recipe[service95edb963-c2b5-469d-b586-38d5ad8f7710::war]")
  end
  only_if { node.run_list.include?("recipe[service95edb963-c2b5-469d-b586-38d5ad8f7710::war]") }
end

ruby_block "remove-deactivate-service95edb963-c2b5-469d-b586-38d5ad8f7710" do
  block do
    node.run_list.remove("recipe[deactivate-service95edb963-c2b5-469d-b586-38d5ad8f7710]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service95edb963-c2b5-469d-b586-38d5ad8f7710]") }
end
