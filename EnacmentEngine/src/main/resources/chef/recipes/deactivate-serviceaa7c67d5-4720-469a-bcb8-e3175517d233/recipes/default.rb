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

ruby_block "remove-serviceaa7c67d5-4720-469a-bcb8-e3175517d233" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceaa7c67d5-4720-469a-bcb8-e3175517d233::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/aa7c67d5-4720-469a-bcb8-e3175517d233.war]", :immediately
end

ruby_block "remove-serviceaa7c67d5-4720-469a-bcb8-e3175517d233" do
  block do
  	node.run_list.remove("recipe[serviceaa7c67d5-4720-469a-bcb8-e3175517d233::war]")
  end
  only_if { node.run_list.include?("recipe[serviceaa7c67d5-4720-469a-bcb8-e3175517d233::war]") }
end

ruby_block "remove-deactivate-serviceaa7c67d5-4720-469a-bcb8-e3175517d233" do
  block do
    node.run_list.remove("recipe[deactivate-serviceaa7c67d5-4720-469a-bcb8-e3175517d233]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceaa7c67d5-4720-469a-bcb8-e3175517d233]") }
end
