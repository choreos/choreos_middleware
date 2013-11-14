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

ruby_block "remove-serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/b9f281ce-7706-4fb7-8641-8bef42e35f8c.war]", :immediately
end

ruby_block "remove-serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c" do
  block do
  	node.run_list.remove("recipe[serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c::war]")
  end
  only_if { node.run_list.include?("recipe[serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c::war]") }
end

ruby_block "remove-deactivate-serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c" do
  block do
    node.run_list.remove("recipe[deactivate-serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceb9f281ce-7706-4fb7-8641-8bef42e35f8c]") }
end
