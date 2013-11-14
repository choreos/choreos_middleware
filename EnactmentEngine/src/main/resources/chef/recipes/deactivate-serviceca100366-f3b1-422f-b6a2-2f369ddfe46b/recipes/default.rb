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

ruby_block "remove-serviceca100366-f3b1-422f-b6a2-2f369ddfe46b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceca100366-f3b1-422f-b6a2-2f369ddfe46b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ca100366-f3b1-422f-b6a2-2f369ddfe46b.war]", :immediately
end

ruby_block "remove-serviceca100366-f3b1-422f-b6a2-2f369ddfe46b" do
  block do
  	node.run_list.remove("recipe[serviceca100366-f3b1-422f-b6a2-2f369ddfe46b::war]")
  end
  only_if { node.run_list.include?("recipe[serviceca100366-f3b1-422f-b6a2-2f369ddfe46b::war]") }
end

ruby_block "remove-deactivate-serviceca100366-f3b1-422f-b6a2-2f369ddfe46b" do
  block do
    node.run_list.remove("recipe[deactivate-serviceca100366-f3b1-422f-b6a2-2f369ddfe46b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceca100366-f3b1-422f-b6a2-2f369ddfe46b]") }
end
