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

ruby_block "remove-service7e798134-184f-4d6a-8bab-e3866fa0881b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7e798134-184f-4d6a-8bab-e3866fa0881b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7e798134-184f-4d6a-8bab-e3866fa0881b.war]", :immediately
end

ruby_block "remove-service7e798134-184f-4d6a-8bab-e3866fa0881b" do
  block do
  	node.run_list.remove("recipe[service7e798134-184f-4d6a-8bab-e3866fa0881b::war]")
  end
  only_if { node.run_list.include?("recipe[service7e798134-184f-4d6a-8bab-e3866fa0881b::war]") }
end

ruby_block "remove-deactivate-service7e798134-184f-4d6a-8bab-e3866fa0881b" do
  block do
    node.run_list.remove("recipe[deactivate-service7e798134-184f-4d6a-8bab-e3866fa0881b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7e798134-184f-4d6a-8bab-e3866fa0881b]") }
end
