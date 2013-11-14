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

ruby_block "remove-servicebccb0264-f3af-4951-a30e-7d1d7153a571" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicebccb0264-f3af-4951-a30e-7d1d7153a571::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/bccb0264-f3af-4951-a30e-7d1d7153a571.war]", :immediately
end

ruby_block "remove-servicebccb0264-f3af-4951-a30e-7d1d7153a571" do
  block do
  	node.run_list.remove("recipe[servicebccb0264-f3af-4951-a30e-7d1d7153a571::war]")
  end
  only_if { node.run_list.include?("recipe[servicebccb0264-f3af-4951-a30e-7d1d7153a571::war]") }
end

ruby_block "remove-deactivate-servicebccb0264-f3af-4951-a30e-7d1d7153a571" do
  block do
    node.run_list.remove("recipe[deactivate-servicebccb0264-f3af-4951-a30e-7d1d7153a571]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicebccb0264-f3af-4951-a30e-7d1d7153a571]") }
end
