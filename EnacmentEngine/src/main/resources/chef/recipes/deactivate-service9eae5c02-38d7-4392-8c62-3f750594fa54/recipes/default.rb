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

ruby_block "remove-service9eae5c02-38d7-4392-8c62-3f750594fa54" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9eae5c02-38d7-4392-8c62-3f750594fa54::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/9eae5c02-38d7-4392-8c62-3f750594fa54.war]", :immediately
end

ruby_block "remove-service9eae5c02-38d7-4392-8c62-3f750594fa54" do
  block do
  	node.run_list.remove("recipe[service9eae5c02-38d7-4392-8c62-3f750594fa54::war]")
  end
  only_if { node.run_list.include?("recipe[service9eae5c02-38d7-4392-8c62-3f750594fa54::war]") }
end

ruby_block "remove-deactivate-service9eae5c02-38d7-4392-8c62-3f750594fa54" do
  block do
    node.run_list.remove("recipe[deactivate-service9eae5c02-38d7-4392-8c62-3f750594fa54]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9eae5c02-38d7-4392-8c62-3f750594fa54]") }
end
