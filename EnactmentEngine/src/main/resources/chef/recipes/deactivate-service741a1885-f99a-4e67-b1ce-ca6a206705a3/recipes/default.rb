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

ruby_block "remove-service741a1885-f99a-4e67-b1ce-ca6a206705a3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service741a1885-f99a-4e67-b1ce-ca6a206705a3::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/741a1885-f99a-4e67-b1ce-ca6a206705a3.war]", :immediately
end

ruby_block "remove-service741a1885-f99a-4e67-b1ce-ca6a206705a3" do
  block do
  	node.run_list.remove("recipe[service741a1885-f99a-4e67-b1ce-ca6a206705a3::war]")
  end
  only_if { node.run_list.include?("recipe[service741a1885-f99a-4e67-b1ce-ca6a206705a3::war]") }
end

ruby_block "remove-deactivate-service741a1885-f99a-4e67-b1ce-ca6a206705a3" do
  block do
    node.run_list.remove("recipe[deactivate-service741a1885-f99a-4e67-b1ce-ca6a206705a3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service741a1885-f99a-4e67-b1ce-ca6a206705a3]") }
end
