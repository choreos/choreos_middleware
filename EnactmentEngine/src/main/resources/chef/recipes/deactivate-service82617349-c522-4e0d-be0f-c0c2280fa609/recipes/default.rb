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

ruby_block "remove-service82617349-c522-4e0d-be0f-c0c2280fa609" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service82617349-c522-4e0d-be0f-c0c2280fa609::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/82617349-c522-4e0d-be0f-c0c2280fa609.war]", :immediately
end

ruby_block "remove-service82617349-c522-4e0d-be0f-c0c2280fa609" do
  block do
  	node.run_list.remove("recipe[service82617349-c522-4e0d-be0f-c0c2280fa609::war]")
  end
  only_if { node.run_list.include?("recipe[service82617349-c522-4e0d-be0f-c0c2280fa609::war]") }
end

ruby_block "remove-deactivate-service82617349-c522-4e0d-be0f-c0c2280fa609" do
  block do
    node.run_list.remove("recipe[deactivate-service82617349-c522-4e0d-be0f-c0c2280fa609]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service82617349-c522-4e0d-be0f-c0c2280fa609]") }
end
