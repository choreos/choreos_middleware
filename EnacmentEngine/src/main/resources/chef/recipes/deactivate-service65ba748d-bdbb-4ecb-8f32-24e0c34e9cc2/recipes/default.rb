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

ruby_block "remove-service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2.war]", :immediately
end

ruby_block "remove-service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2" do
  block do
  	node.run_list.remove("recipe[service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2::war]")
  end
  only_if { node.run_list.include?("recipe[service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2::war]") }
end

ruby_block "remove-deactivate-service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2" do
  block do
    node.run_list.remove("recipe[deactivate-service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service65ba748d-bdbb-4ecb-8f32-24e0c34e9cc2]") }
end
