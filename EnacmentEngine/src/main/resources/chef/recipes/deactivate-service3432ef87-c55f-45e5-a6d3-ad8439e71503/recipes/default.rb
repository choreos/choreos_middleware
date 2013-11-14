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

ruby_block "remove-service3432ef87-c55f-45e5-a6d3-ad8439e71503" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service3432ef87-c55f-45e5-a6d3-ad8439e71503::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/3432ef87-c55f-45e5-a6d3-ad8439e71503.war]", :immediately
end

ruby_block "remove-service3432ef87-c55f-45e5-a6d3-ad8439e71503" do
  block do
  	node.run_list.remove("recipe[service3432ef87-c55f-45e5-a6d3-ad8439e71503::war]")
  end
  only_if { node.run_list.include?("recipe[service3432ef87-c55f-45e5-a6d3-ad8439e71503::war]") }
end

ruby_block "remove-deactivate-service3432ef87-c55f-45e5-a6d3-ad8439e71503" do
  block do
    node.run_list.remove("recipe[deactivate-service3432ef87-c55f-45e5-a6d3-ad8439e71503]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service3432ef87-c55f-45e5-a6d3-ad8439e71503]") }
end
