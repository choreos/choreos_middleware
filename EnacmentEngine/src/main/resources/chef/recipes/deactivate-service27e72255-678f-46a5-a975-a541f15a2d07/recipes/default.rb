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

ruby_block "remove-service27e72255-678f-46a5-a975-a541f15a2d07" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service27e72255-678f-46a5-a975-a541f15a2d07::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/27e72255-678f-46a5-a975-a541f15a2d07.war]", :immediately
end

ruby_block "remove-service27e72255-678f-46a5-a975-a541f15a2d07" do
  block do
  	node.run_list.remove("recipe[service27e72255-678f-46a5-a975-a541f15a2d07::war]")
  end
  only_if { node.run_list.include?("recipe[service27e72255-678f-46a5-a975-a541f15a2d07::war]") }
end

ruby_block "remove-deactivate-service27e72255-678f-46a5-a975-a541f15a2d07" do
  block do
    node.run_list.remove("recipe[deactivate-service27e72255-678f-46a5-a975-a541f15a2d07]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service27e72255-678f-46a5-a975-a541f15a2d07]") }
end
