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

ruby_block "remove-servicef0e62931-4e55-4f09-9442-e0f219f6da61" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef0e62931-4e55-4f09-9442-e0f219f6da61::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f0e62931-4e55-4f09-9442-e0f219f6da61.war]", :immediately
end

ruby_block "remove-servicef0e62931-4e55-4f09-9442-e0f219f6da61" do
  block do
  	node.run_list.remove("recipe[servicef0e62931-4e55-4f09-9442-e0f219f6da61::war]")
  end
  only_if { node.run_list.include?("recipe[servicef0e62931-4e55-4f09-9442-e0f219f6da61::war]") }
end

ruby_block "remove-deactivate-servicef0e62931-4e55-4f09-9442-e0f219f6da61" do
  block do
    node.run_list.remove("recipe[deactivate-servicef0e62931-4e55-4f09-9442-e0f219f6da61]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef0e62931-4e55-4f09-9442-e0f219f6da61]") }
end
