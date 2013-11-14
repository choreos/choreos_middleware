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

ruby_block "remove-service7cb8cc32-b6e4-4645-874e-d463705a55f7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7cb8cc32-b6e4-4645-874e-d463705a55f7::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7cb8cc32-b6e4-4645-874e-d463705a55f7.war]", :immediately
end

ruby_block "remove-service7cb8cc32-b6e4-4645-874e-d463705a55f7" do
  block do
  	node.run_list.remove("recipe[service7cb8cc32-b6e4-4645-874e-d463705a55f7::war]")
  end
  only_if { node.run_list.include?("recipe[service7cb8cc32-b6e4-4645-874e-d463705a55f7::war]") }
end

ruby_block "remove-deactivate-service7cb8cc32-b6e4-4645-874e-d463705a55f7" do
  block do
    node.run_list.remove("recipe[deactivate-service7cb8cc32-b6e4-4645-874e-d463705a55f7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7cb8cc32-b6e4-4645-874e-d463705a55f7]") }
end
