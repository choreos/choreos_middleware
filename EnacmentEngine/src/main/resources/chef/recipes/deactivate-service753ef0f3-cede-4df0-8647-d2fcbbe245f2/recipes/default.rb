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

ruby_block "remove-service753ef0f3-cede-4df0-8647-d2fcbbe245f2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service753ef0f3-cede-4df0-8647-d2fcbbe245f2::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/753ef0f3-cede-4df0-8647-d2fcbbe245f2.war]", :immediately
end

ruby_block "remove-service753ef0f3-cede-4df0-8647-d2fcbbe245f2" do
  block do
  	node.run_list.remove("recipe[service753ef0f3-cede-4df0-8647-d2fcbbe245f2::war]")
  end
  only_if { node.run_list.include?("recipe[service753ef0f3-cede-4df0-8647-d2fcbbe245f2::war]") }
end

ruby_block "remove-deactivate-service753ef0f3-cede-4df0-8647-d2fcbbe245f2" do
  block do
    node.run_list.remove("recipe[deactivate-service753ef0f3-cede-4df0-8647-d2fcbbe245f2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service753ef0f3-cede-4df0-8647-d2fcbbe245f2]") }
end
