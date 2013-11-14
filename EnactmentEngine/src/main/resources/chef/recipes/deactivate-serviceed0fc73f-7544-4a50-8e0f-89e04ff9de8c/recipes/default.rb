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

ruby_block "remove-serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ed0fc73f-7544-4a50-8e0f-89e04ff9de8c.war]", :immediately
end

ruby_block "remove-serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c" do
  block do
  	node.run_list.remove("recipe[serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c::war]")
  end
  only_if { node.run_list.include?("recipe[serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c::war]") }
end

ruby_block "remove-deactivate-serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c" do
  block do
    node.run_list.remove("recipe[deactivate-serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceed0fc73f-7544-4a50-8e0f-89e04ff9de8c]") }
end
