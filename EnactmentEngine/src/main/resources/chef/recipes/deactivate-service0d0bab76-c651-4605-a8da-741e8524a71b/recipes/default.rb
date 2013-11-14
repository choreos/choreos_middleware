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

ruby_block "remove-service0d0bab76-c651-4605-a8da-741e8524a71b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service0d0bab76-c651-4605-a8da-741e8524a71b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/0d0bab76-c651-4605-a8da-741e8524a71b.war]", :immediately
end

ruby_block "remove-service0d0bab76-c651-4605-a8da-741e8524a71b" do
  block do
  	node.run_list.remove("recipe[service0d0bab76-c651-4605-a8da-741e8524a71b::war]")
  end
  only_if { node.run_list.include?("recipe[service0d0bab76-c651-4605-a8da-741e8524a71b::war]") }
end

ruby_block "remove-deactivate-service0d0bab76-c651-4605-a8da-741e8524a71b" do
  block do
    node.run_list.remove("recipe[deactivate-service0d0bab76-c651-4605-a8da-741e8524a71b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service0d0bab76-c651-4605-a8da-741e8524a71b]") }
end
