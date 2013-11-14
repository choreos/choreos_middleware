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

ruby_block "remove-service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b.war]", :immediately
end

ruby_block "remove-service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b" do
  block do
  	node.run_list.remove("recipe[service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b::war]")
  end
  only_if { node.run_list.include?("recipe[service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b::war]") }
end

ruby_block "remove-deactivate-service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b" do
  block do
    node.run_list.remove("recipe[deactivate-service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2d8f2a48-7cd7-4f98-95a9-deffd0c5e33b]") }
end
