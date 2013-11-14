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

ruby_block "remove-service3e080980-285b-4e00-ba41-9671bf30dca3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service3e080980-285b-4e00-ba41-9671bf30dca3::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/3e080980-285b-4e00-ba41-9671bf30dca3.war]", :immediately
end

ruby_block "remove-service3e080980-285b-4e00-ba41-9671bf30dca3" do
  block do
  	node.run_list.remove("recipe[service3e080980-285b-4e00-ba41-9671bf30dca3::war]")
  end
  only_if { node.run_list.include?("recipe[service3e080980-285b-4e00-ba41-9671bf30dca3::war]") }
end

ruby_block "remove-deactivate-service3e080980-285b-4e00-ba41-9671bf30dca3" do
  block do
    node.run_list.remove("recipe[deactivate-service3e080980-285b-4e00-ba41-9671bf30dca3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3e080980-285b-4e00-ba41-9671bf30dca3]") }
end
