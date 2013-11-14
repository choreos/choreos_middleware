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

ruby_block "remove-servicefd87d081-a547-4c40-b0a8-ac9db04266ff" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefd87d081-a547-4c40-b0a8-ac9db04266ff::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/fd87d081-a547-4c40-b0a8-ac9db04266ff.war]", :immediately
end

ruby_block "remove-servicefd87d081-a547-4c40-b0a8-ac9db04266ff" do
  block do
  	node.run_list.remove("recipe[servicefd87d081-a547-4c40-b0a8-ac9db04266ff::war]")
  end
  only_if { node.run_list.include?("recipe[servicefd87d081-a547-4c40-b0a8-ac9db04266ff::war]") }
end

ruby_block "remove-deactivate-servicefd87d081-a547-4c40-b0a8-ac9db04266ff" do
  block do
    node.run_list.remove("recipe[deactivate-servicefd87d081-a547-4c40-b0a8-ac9db04266ff]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefd87d081-a547-4c40-b0a8-ac9db04266ff]") }
end
