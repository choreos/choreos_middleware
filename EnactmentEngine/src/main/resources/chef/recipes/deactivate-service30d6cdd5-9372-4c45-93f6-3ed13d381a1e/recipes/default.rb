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

ruby_block "remove-service30d6cdd5-9372-4c45-93f6-3ed13d381a1e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service30d6cdd5-9372-4c45-93f6-3ed13d381a1e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/30d6cdd5-9372-4c45-93f6-3ed13d381a1e.war]", :immediately
end

ruby_block "remove-service30d6cdd5-9372-4c45-93f6-3ed13d381a1e" do
  block do
  	node.run_list.remove("recipe[service30d6cdd5-9372-4c45-93f6-3ed13d381a1e::war]")
  end
  only_if { node.run_list.include?("recipe[service30d6cdd5-9372-4c45-93f6-3ed13d381a1e::war]") }
end

ruby_block "remove-deactivate-service30d6cdd5-9372-4c45-93f6-3ed13d381a1e" do
  block do
    node.run_list.remove("recipe[deactivate-service30d6cdd5-9372-4c45-93f6-3ed13d381a1e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service30d6cdd5-9372-4c45-93f6-3ed13d381a1e]") }
end
