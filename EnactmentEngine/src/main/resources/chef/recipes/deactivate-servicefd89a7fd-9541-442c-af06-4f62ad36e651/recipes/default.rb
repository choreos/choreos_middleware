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

ruby_block "remove-servicefd89a7fd-9541-442c-af06-4f62ad36e651" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicefd89a7fd-9541-442c-af06-4f62ad36e651::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/fd89a7fd-9541-442c-af06-4f62ad36e651.war]", :immediately
end

ruby_block "remove-servicefd89a7fd-9541-442c-af06-4f62ad36e651" do
  block do
  	node.run_list.remove("recipe[servicefd89a7fd-9541-442c-af06-4f62ad36e651::war]")
  end
  only_if { node.run_list.include?("recipe[servicefd89a7fd-9541-442c-af06-4f62ad36e651::war]") }
end

ruby_block "remove-deactivate-servicefd89a7fd-9541-442c-af06-4f62ad36e651" do
  block do
    node.run_list.remove("recipe[deactivate-servicefd89a7fd-9541-442c-af06-4f62ad36e651]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicefd89a7fd-9541-442c-af06-4f62ad36e651]") }
end
