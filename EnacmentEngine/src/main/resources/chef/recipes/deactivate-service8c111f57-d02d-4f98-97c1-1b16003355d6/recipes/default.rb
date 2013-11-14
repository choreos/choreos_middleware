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

ruby_block "remove-service8c111f57-d02d-4f98-97c1-1b16003355d6" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service8c111f57-d02d-4f98-97c1-1b16003355d6::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/8c111f57-d02d-4f98-97c1-1b16003355d6.war]", :immediately
end

ruby_block "remove-service8c111f57-d02d-4f98-97c1-1b16003355d6" do
  block do
  	node.run_list.remove("recipe[service8c111f57-d02d-4f98-97c1-1b16003355d6::war]")
  end
  only_if { node.run_list.include?("recipe[service8c111f57-d02d-4f98-97c1-1b16003355d6::war]") }
end

ruby_block "remove-deactivate-service8c111f57-d02d-4f98-97c1-1b16003355d6" do
  block do
    node.run_list.remove("recipe[deactivate-service8c111f57-d02d-4f98-97c1-1b16003355d6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service8c111f57-d02d-4f98-97c1-1b16003355d6]") }
end
