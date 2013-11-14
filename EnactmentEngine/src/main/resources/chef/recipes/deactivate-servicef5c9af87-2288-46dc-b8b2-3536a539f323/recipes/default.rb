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

ruby_block "remove-servicef5c9af87-2288-46dc-b8b2-3536a539f323" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef5c9af87-2288-46dc-b8b2-3536a539f323::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f5c9af87-2288-46dc-b8b2-3536a539f323.war]", :immediately
end

ruby_block "remove-servicef5c9af87-2288-46dc-b8b2-3536a539f323" do
  block do
  	node.run_list.remove("recipe[servicef5c9af87-2288-46dc-b8b2-3536a539f323::war]")
  end
  only_if { node.run_list.include?("recipe[servicef5c9af87-2288-46dc-b8b2-3536a539f323::war]") }
end

ruby_block "remove-deactivate-servicef5c9af87-2288-46dc-b8b2-3536a539f323" do
  block do
    node.run_list.remove("recipe[deactivate-servicef5c9af87-2288-46dc-b8b2-3536a539f323]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef5c9af87-2288-46dc-b8b2-3536a539f323]") }
end
