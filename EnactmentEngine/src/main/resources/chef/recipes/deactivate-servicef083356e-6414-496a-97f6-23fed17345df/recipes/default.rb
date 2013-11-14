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

ruby_block "remove-servicef083356e-6414-496a-97f6-23fed17345df" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef083356e-6414-496a-97f6-23fed17345df::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f083356e-6414-496a-97f6-23fed17345df.war]", :immediately
end

ruby_block "remove-servicef083356e-6414-496a-97f6-23fed17345df" do
  block do
  	node.run_list.remove("recipe[servicef083356e-6414-496a-97f6-23fed17345df::war]")
  end
  only_if { node.run_list.include?("recipe[servicef083356e-6414-496a-97f6-23fed17345df::war]") }
end

ruby_block "remove-deactivate-servicef083356e-6414-496a-97f6-23fed17345df" do
  block do
    node.run_list.remove("recipe[deactivate-servicef083356e-6414-496a-97f6-23fed17345df]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef083356e-6414-496a-97f6-23fed17345df]") }
end
