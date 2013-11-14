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

ruby_block "remove-serviced9b47a43-95a0-4b5b-89b6-895afff0c87c" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced9b47a43-95a0-4b5b-89b6-895afff0c87c::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d9b47a43-95a0-4b5b-89b6-895afff0c87c.war]", :immediately
end

ruby_block "remove-serviced9b47a43-95a0-4b5b-89b6-895afff0c87c" do
  block do
  	node.run_list.remove("recipe[serviced9b47a43-95a0-4b5b-89b6-895afff0c87c::war]")
  end
  only_if { node.run_list.include?("recipe[serviced9b47a43-95a0-4b5b-89b6-895afff0c87c::war]") }
end

ruby_block "remove-deactivate-serviced9b47a43-95a0-4b5b-89b6-895afff0c87c" do
  block do
    node.run_list.remove("recipe[deactivate-serviced9b47a43-95a0-4b5b-89b6-895afff0c87c]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced9b47a43-95a0-4b5b-89b6-895afff0c87c]") }
end
