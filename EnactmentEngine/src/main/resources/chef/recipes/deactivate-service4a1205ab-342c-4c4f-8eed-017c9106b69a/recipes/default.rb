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

ruby_block "remove-service4a1205ab-342c-4c4f-8eed-017c9106b69a" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4a1205ab-342c-4c4f-8eed-017c9106b69a::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4a1205ab-342c-4c4f-8eed-017c9106b69a.war]", :immediately
end

ruby_block "remove-service4a1205ab-342c-4c4f-8eed-017c9106b69a" do
  block do
  	node.run_list.remove("recipe[service4a1205ab-342c-4c4f-8eed-017c9106b69a::war]")
  end
  only_if { node.run_list.include?("recipe[service4a1205ab-342c-4c4f-8eed-017c9106b69a::war]") }
end

ruby_block "remove-deactivate-service4a1205ab-342c-4c4f-8eed-017c9106b69a" do
  block do
    node.run_list.remove("recipe[deactivate-service4a1205ab-342c-4c4f-8eed-017c9106b69a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4a1205ab-342c-4c4f-8eed-017c9106b69a]") }
end
