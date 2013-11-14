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

ruby_block "remove-service986ef9c1-dc45-4e26-b045-a86b57aa343c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service986ef9c1-dc45-4e26-b045-a86b57aa343c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/986ef9c1-dc45-4e26-b045-a86b57aa343c.war]", :immediately
end

ruby_block "remove-service986ef9c1-dc45-4e26-b045-a86b57aa343c" do
  block do
  	node.run_list.remove("recipe[service986ef9c1-dc45-4e26-b045-a86b57aa343c::war]")
  end
  only_if { node.run_list.include?("recipe[service986ef9c1-dc45-4e26-b045-a86b57aa343c::war]") }
end

ruby_block "remove-deactivate-service986ef9c1-dc45-4e26-b045-a86b57aa343c" do
  block do
    node.run_list.remove("recipe[deactivate-service986ef9c1-dc45-4e26-b045-a86b57aa343c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service986ef9c1-dc45-4e26-b045-a86b57aa343c]") }
end
