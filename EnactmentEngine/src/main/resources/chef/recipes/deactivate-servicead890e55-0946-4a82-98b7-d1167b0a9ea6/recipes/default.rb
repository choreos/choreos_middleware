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

ruby_block "remove-servicead890e55-0946-4a82-98b7-d1167b0a9ea6" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicead890e55-0946-4a82-98b7-d1167b0a9ea6::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ad890e55-0946-4a82-98b7-d1167b0a9ea6.war]", :immediately
end

ruby_block "remove-servicead890e55-0946-4a82-98b7-d1167b0a9ea6" do
  block do
  	node.run_list.remove("recipe[servicead890e55-0946-4a82-98b7-d1167b0a9ea6::war]")
  end
  only_if { node.run_list.include?("recipe[servicead890e55-0946-4a82-98b7-d1167b0a9ea6::war]") }
end

ruby_block "remove-deactivate-servicead890e55-0946-4a82-98b7-d1167b0a9ea6" do
  block do
    node.run_list.remove("recipe[deactivate-servicead890e55-0946-4a82-98b7-d1167b0a9ea6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicead890e55-0946-4a82-98b7-d1167b0a9ea6]") }
end
