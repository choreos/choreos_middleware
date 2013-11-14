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

ruby_block "remove-servicee72c3554-c41b-4e7c-8ad5-6cba3350c432" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee72c3554-c41b-4e7c-8ad5-6cba3350c432::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e72c3554-c41b-4e7c-8ad5-6cba3350c432.war]", :immediately
end

ruby_block "remove-servicee72c3554-c41b-4e7c-8ad5-6cba3350c432" do
  block do
  	node.run_list.remove("recipe[servicee72c3554-c41b-4e7c-8ad5-6cba3350c432::war]")
  end
  only_if { node.run_list.include?("recipe[servicee72c3554-c41b-4e7c-8ad5-6cba3350c432::war]") }
end

ruby_block "remove-deactivate-servicee72c3554-c41b-4e7c-8ad5-6cba3350c432" do
  block do
    node.run_list.remove("recipe[deactivate-servicee72c3554-c41b-4e7c-8ad5-6cba3350c432]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee72c3554-c41b-4e7c-8ad5-6cba3350c432]") }
end
