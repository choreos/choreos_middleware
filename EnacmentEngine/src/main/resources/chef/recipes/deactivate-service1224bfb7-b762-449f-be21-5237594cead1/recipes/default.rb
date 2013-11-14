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

ruby_block "remove-service1224bfb7-b762-449f-be21-5237594cead1" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1224bfb7-b762-449f-be21-5237594cead1::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1224bfb7-b762-449f-be21-5237594cead1.war]", :immediately
end

ruby_block "remove-service1224bfb7-b762-449f-be21-5237594cead1" do
  block do
  	node.run_list.remove("recipe[service1224bfb7-b762-449f-be21-5237594cead1::war]")
  end
  only_if { node.run_list.include?("recipe[service1224bfb7-b762-449f-be21-5237594cead1::war]") }
end

ruby_block "remove-deactivate-service1224bfb7-b762-449f-be21-5237594cead1" do
  block do
    node.run_list.remove("recipe[deactivate-service1224bfb7-b762-449f-be21-5237594cead1]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1224bfb7-b762-449f-be21-5237594cead1]") }
end