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

ruby_block "remove-servicec6b21301-398f-4fd0-8b8f-c87b783260ff" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec6b21301-398f-4fd0-8b8f-c87b783260ff::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c6b21301-398f-4fd0-8b8f-c87b783260ff.war]", :immediately
end

ruby_block "remove-servicec6b21301-398f-4fd0-8b8f-c87b783260ff" do
  block do
  	node.run_list.remove("recipe[servicec6b21301-398f-4fd0-8b8f-c87b783260ff::war]")
  end
  only_if { node.run_list.include?("recipe[servicec6b21301-398f-4fd0-8b8f-c87b783260ff::war]") }
end

ruby_block "remove-deactivate-servicec6b21301-398f-4fd0-8b8f-c87b783260ff" do
  block do
    node.run_list.remove("recipe[deactivate-servicec6b21301-398f-4fd0-8b8f-c87b783260ff]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec6b21301-398f-4fd0-8b8f-c87b783260ff]") }
end
