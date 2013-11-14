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

ruby_block "remove-service79edcdba-f48b-4d47-a20f-dc30e695be21" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service79edcdba-f48b-4d47-a20f-dc30e695be21::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/79edcdba-f48b-4d47-a20f-dc30e695be21.war]", :immediately
end

ruby_block "remove-service79edcdba-f48b-4d47-a20f-dc30e695be21" do
  block do
  	node.run_list.remove("recipe[service79edcdba-f48b-4d47-a20f-dc30e695be21::war]")
  end
  only_if { node.run_list.include?("recipe[service79edcdba-f48b-4d47-a20f-dc30e695be21::war]") }
end

ruby_block "remove-deactivate-service79edcdba-f48b-4d47-a20f-dc30e695be21" do
  block do
    node.run_list.remove("recipe[deactivate-service79edcdba-f48b-4d47-a20f-dc30e695be21]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service79edcdba-f48b-4d47-a20f-dc30e695be21]") }
end
