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

ruby_block "remove-service89cb5a90-945f-41a6-a2f5-7d353bad33cb" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service89cb5a90-945f-41a6-a2f5-7d353bad33cb::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/89cb5a90-945f-41a6-a2f5-7d353bad33cb.war]", :immediately
end

ruby_block "remove-service89cb5a90-945f-41a6-a2f5-7d353bad33cb" do
  block do
  	node.run_list.remove("recipe[service89cb5a90-945f-41a6-a2f5-7d353bad33cb::war]")
  end
  only_if { node.run_list.include?("recipe[service89cb5a90-945f-41a6-a2f5-7d353bad33cb::war]") }
end

ruby_block "remove-deactivate-service89cb5a90-945f-41a6-a2f5-7d353bad33cb" do
  block do
    node.run_list.remove("recipe[deactivate-service89cb5a90-945f-41a6-a2f5-7d353bad33cb]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service89cb5a90-945f-41a6-a2f5-7d353bad33cb]") }
end
