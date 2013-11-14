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

ruby_block "remove-service59271e33-b1b4-4b10-bf00-ee589f90d92d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service59271e33-b1b4-4b10-bf00-ee589f90d92d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/59271e33-b1b4-4b10-bf00-ee589f90d92d.war]", :immediately
end

ruby_block "remove-service59271e33-b1b4-4b10-bf00-ee589f90d92d" do
  block do
  	node.run_list.remove("recipe[service59271e33-b1b4-4b10-bf00-ee589f90d92d::war]")
  end
  only_if { node.run_list.include?("recipe[service59271e33-b1b4-4b10-bf00-ee589f90d92d::war]") }
end

ruby_block "remove-deactivate-service59271e33-b1b4-4b10-bf00-ee589f90d92d" do
  block do
    node.run_list.remove("recipe[deactivate-service59271e33-b1b4-4b10-bf00-ee589f90d92d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service59271e33-b1b4-4b10-bf00-ee589f90d92d]") }
end
