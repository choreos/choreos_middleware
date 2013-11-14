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

ruby_block "remove-servicec78b6583-dd1f-477f-a3d6-e301855f7d6f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec78b6583-dd1f-477f-a3d6-e301855f7d6f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c78b6583-dd1f-477f-a3d6-e301855f7d6f.war]", :immediately
end

ruby_block "remove-servicec78b6583-dd1f-477f-a3d6-e301855f7d6f" do
  block do
  	node.run_list.remove("recipe[servicec78b6583-dd1f-477f-a3d6-e301855f7d6f::war]")
  end
  only_if { node.run_list.include?("recipe[servicec78b6583-dd1f-477f-a3d6-e301855f7d6f::war]") }
end

ruby_block "remove-deactivate-servicec78b6583-dd1f-477f-a3d6-e301855f7d6f" do
  block do
    node.run_list.remove("recipe[deactivate-servicec78b6583-dd1f-477f-a3d6-e301855f7d6f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec78b6583-dd1f-477f-a3d6-e301855f7d6f]") }
end
