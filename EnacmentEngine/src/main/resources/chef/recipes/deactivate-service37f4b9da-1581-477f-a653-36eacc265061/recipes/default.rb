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

ruby_block "remove-service37f4b9da-1581-477f-a653-36eacc265061" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service37f4b9da-1581-477f-a653-36eacc265061::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/37f4b9da-1581-477f-a653-36eacc265061.war]", :immediately
end

ruby_block "remove-service37f4b9da-1581-477f-a653-36eacc265061" do
  block do
  	node.run_list.remove("recipe[service37f4b9da-1581-477f-a653-36eacc265061::war]")
  end
  only_if { node.run_list.include?("recipe[service37f4b9da-1581-477f-a653-36eacc265061::war]") }
end

ruby_block "remove-deactivate-service37f4b9da-1581-477f-a653-36eacc265061" do
  block do
    node.run_list.remove("recipe[deactivate-service37f4b9da-1581-477f-a653-36eacc265061]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service37f4b9da-1581-477f-a653-36eacc265061]") }
end
