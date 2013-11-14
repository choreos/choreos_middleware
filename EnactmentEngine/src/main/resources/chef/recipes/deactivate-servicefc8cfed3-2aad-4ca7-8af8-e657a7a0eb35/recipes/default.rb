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

ruby_block "remove-servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/fc8cfed3-2aad-4ca7-8af8-e657a7a0eb35.war]", :immediately
end

ruby_block "remove-servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35" do
  block do
  	node.run_list.remove("recipe[servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35::war]")
  end
  only_if { node.run_list.include?("recipe[servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35::war]") }
end

ruby_block "remove-deactivate-servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35" do
  block do
    node.run_list.remove("recipe[deactivate-servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefc8cfed3-2aad-4ca7-8af8-e657a7a0eb35]") }
end
