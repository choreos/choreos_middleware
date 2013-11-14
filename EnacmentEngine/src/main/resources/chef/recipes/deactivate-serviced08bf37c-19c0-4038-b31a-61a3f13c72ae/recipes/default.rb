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

ruby_block "remove-serviced08bf37c-19c0-4038-b31a-61a3f13c72ae" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced08bf37c-19c0-4038-b31a-61a3f13c72ae::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d08bf37c-19c0-4038-b31a-61a3f13c72ae.war]", :immediately
end

ruby_block "remove-serviced08bf37c-19c0-4038-b31a-61a3f13c72ae" do
  block do
  	node.run_list.remove("recipe[serviced08bf37c-19c0-4038-b31a-61a3f13c72ae::war]")
  end
  only_if { node.run_list.include?("recipe[serviced08bf37c-19c0-4038-b31a-61a3f13c72ae::war]") }
end

ruby_block "remove-deactivate-serviced08bf37c-19c0-4038-b31a-61a3f13c72ae" do
  block do
    node.run_list.remove("recipe[deactivate-serviced08bf37c-19c0-4038-b31a-61a3f13c72ae]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced08bf37c-19c0-4038-b31a-61a3f13c72ae]") }
end
