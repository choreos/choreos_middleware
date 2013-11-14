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

ruby_block "remove-service9148e047-7358-49aa-a3ec-9fffd6f36a2d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9148e047-7358-49aa-a3ec-9fffd6f36a2d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/9148e047-7358-49aa-a3ec-9fffd6f36a2d.war]", :immediately
end

ruby_block "remove-service9148e047-7358-49aa-a3ec-9fffd6f36a2d" do
  block do
  	node.run_list.remove("recipe[service9148e047-7358-49aa-a3ec-9fffd6f36a2d::war]")
  end
  only_if { node.run_list.include?("recipe[service9148e047-7358-49aa-a3ec-9fffd6f36a2d::war]") }
end

ruby_block "remove-deactivate-service9148e047-7358-49aa-a3ec-9fffd6f36a2d" do
  block do
    node.run_list.remove("recipe[deactivate-service9148e047-7358-49aa-a3ec-9fffd6f36a2d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9148e047-7358-49aa-a3ec-9fffd6f36a2d]") }
end
