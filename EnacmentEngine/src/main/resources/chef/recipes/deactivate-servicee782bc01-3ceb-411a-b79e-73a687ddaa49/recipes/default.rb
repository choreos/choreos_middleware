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

ruby_block "remove-servicee782bc01-3ceb-411a-b79e-73a687ddaa49" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee782bc01-3ceb-411a-b79e-73a687ddaa49::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e782bc01-3ceb-411a-b79e-73a687ddaa49.war]", :immediately
end

ruby_block "remove-servicee782bc01-3ceb-411a-b79e-73a687ddaa49" do
  block do
  	node.run_list.remove("recipe[servicee782bc01-3ceb-411a-b79e-73a687ddaa49::war]")
  end
  only_if { node.run_list.include?("recipe[servicee782bc01-3ceb-411a-b79e-73a687ddaa49::war]") }
end

ruby_block "remove-deactivate-servicee782bc01-3ceb-411a-b79e-73a687ddaa49" do
  block do
    node.run_list.remove("recipe[deactivate-servicee782bc01-3ceb-411a-b79e-73a687ddaa49]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee782bc01-3ceb-411a-b79e-73a687ddaa49]") }
end
