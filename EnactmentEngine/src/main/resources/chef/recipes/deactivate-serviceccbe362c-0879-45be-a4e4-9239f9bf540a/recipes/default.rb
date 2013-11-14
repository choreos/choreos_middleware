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

ruby_block "remove-serviceccbe362c-0879-45be-a4e4-9239f9bf540a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceccbe362c-0879-45be-a4e4-9239f9bf540a::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ccbe362c-0879-45be-a4e4-9239f9bf540a.war]", :immediately
end

ruby_block "remove-serviceccbe362c-0879-45be-a4e4-9239f9bf540a" do
  block do
  	node.run_list.remove("recipe[serviceccbe362c-0879-45be-a4e4-9239f9bf540a::war]")
  end
  only_if { node.run_list.include?("recipe[serviceccbe362c-0879-45be-a4e4-9239f9bf540a::war]") }
end

ruby_block "remove-deactivate-serviceccbe362c-0879-45be-a4e4-9239f9bf540a" do
  block do
    node.run_list.remove("recipe[deactivate-serviceccbe362c-0879-45be-a4e4-9239f9bf540a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceccbe362c-0879-45be-a4e4-9239f9bf540a]") }
end
