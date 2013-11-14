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

ruby_block "remove-servicedf6f7b76-c0d2-45e3-b216-8b599b27436d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicedf6f7b76-c0d2-45e3-b216-8b599b27436d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/df6f7b76-c0d2-45e3-b216-8b599b27436d.war]", :immediately
end

ruby_block "remove-servicedf6f7b76-c0d2-45e3-b216-8b599b27436d" do
  block do
  	node.run_list.remove("recipe[servicedf6f7b76-c0d2-45e3-b216-8b599b27436d::war]")
  end
  only_if { node.run_list.include?("recipe[servicedf6f7b76-c0d2-45e3-b216-8b599b27436d::war]") }
end

ruby_block "remove-deactivate-servicedf6f7b76-c0d2-45e3-b216-8b599b27436d" do
  block do
    node.run_list.remove("recipe[deactivate-servicedf6f7b76-c0d2-45e3-b216-8b599b27436d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicedf6f7b76-c0d2-45e3-b216-8b599b27436d]") }
end
