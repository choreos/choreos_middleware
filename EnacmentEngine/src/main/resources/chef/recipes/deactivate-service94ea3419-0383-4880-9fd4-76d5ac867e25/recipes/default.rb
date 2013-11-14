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

ruby_block "remove-service94ea3419-0383-4880-9fd4-76d5ac867e25" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service94ea3419-0383-4880-9fd4-76d5ac867e25::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/94ea3419-0383-4880-9fd4-76d5ac867e25.war]", :immediately
end

ruby_block "remove-service94ea3419-0383-4880-9fd4-76d5ac867e25" do
  block do
  	node.run_list.remove("recipe[service94ea3419-0383-4880-9fd4-76d5ac867e25::war]")
  end
  only_if { node.run_list.include?("recipe[service94ea3419-0383-4880-9fd4-76d5ac867e25::war]") }
end

ruby_block "remove-deactivate-service94ea3419-0383-4880-9fd4-76d5ac867e25" do
  block do
    node.run_list.remove("recipe[deactivate-service94ea3419-0383-4880-9fd4-76d5ac867e25]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service94ea3419-0383-4880-9fd4-76d5ac867e25]") }
end
