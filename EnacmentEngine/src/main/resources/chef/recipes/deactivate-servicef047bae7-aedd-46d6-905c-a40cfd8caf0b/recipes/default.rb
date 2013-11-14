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

ruby_block "remove-servicef047bae7-aedd-46d6-905c-a40cfd8caf0b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef047bae7-aedd-46d6-905c-a40cfd8caf0b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f047bae7-aedd-46d6-905c-a40cfd8caf0b.war]", :immediately
end

ruby_block "remove-servicef047bae7-aedd-46d6-905c-a40cfd8caf0b" do
  block do
  	node.run_list.remove("recipe[servicef047bae7-aedd-46d6-905c-a40cfd8caf0b::war]")
  end
  only_if { node.run_list.include?("recipe[servicef047bae7-aedd-46d6-905c-a40cfd8caf0b::war]") }
end

ruby_block "remove-deactivate-servicef047bae7-aedd-46d6-905c-a40cfd8caf0b" do
  block do
    node.run_list.remove("recipe[deactivate-servicef047bae7-aedd-46d6-905c-a40cfd8caf0b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef047bae7-aedd-46d6-905c-a40cfd8caf0b]") }
end
