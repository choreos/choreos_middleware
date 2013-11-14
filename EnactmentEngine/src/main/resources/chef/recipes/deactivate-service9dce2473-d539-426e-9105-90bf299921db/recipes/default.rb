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

ruby_block "remove-service9dce2473-d539-426e-9105-90bf299921db" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9dce2473-d539-426e-9105-90bf299921db::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/9dce2473-d539-426e-9105-90bf299921db.war]", :immediately
end

ruby_block "remove-service9dce2473-d539-426e-9105-90bf299921db" do
  block do
  	node.run_list.remove("recipe[service9dce2473-d539-426e-9105-90bf299921db::war]")
  end
  only_if { node.run_list.include?("recipe[service9dce2473-d539-426e-9105-90bf299921db::war]") }
end

ruby_block "remove-deactivate-service9dce2473-d539-426e-9105-90bf299921db" do
  block do
    node.run_list.remove("recipe[deactivate-service9dce2473-d539-426e-9105-90bf299921db]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9dce2473-d539-426e-9105-90bf299921db]") }
end
