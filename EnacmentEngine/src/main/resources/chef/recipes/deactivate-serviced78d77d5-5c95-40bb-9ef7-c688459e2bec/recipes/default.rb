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

ruby_block "remove-serviced78d77d5-5c95-40bb-9ef7-c688459e2bec" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced78d77d5-5c95-40bb-9ef7-c688459e2bec::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d78d77d5-5c95-40bb-9ef7-c688459e2bec.war]", :immediately
end

ruby_block "remove-serviced78d77d5-5c95-40bb-9ef7-c688459e2bec" do
  block do
  	node.run_list.remove("recipe[serviced78d77d5-5c95-40bb-9ef7-c688459e2bec::war]")
  end
  only_if { node.run_list.include?("recipe[serviced78d77d5-5c95-40bb-9ef7-c688459e2bec::war]") }
end

ruby_block "remove-deactivate-serviced78d77d5-5c95-40bb-9ef7-c688459e2bec" do
  block do
    node.run_list.remove("recipe[deactivate-serviced78d77d5-5c95-40bb-9ef7-c688459e2bec]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced78d77d5-5c95-40bb-9ef7-c688459e2bec]") }
end
