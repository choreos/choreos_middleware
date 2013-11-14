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

ruby_block "remove-service7a028df1-bdf6-46ae-885e-f58a5b46faea" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7a028df1-bdf6-46ae-885e-f58a5b46faea::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7a028df1-bdf6-46ae-885e-f58a5b46faea.war]", :immediately
end

ruby_block "remove-service7a028df1-bdf6-46ae-885e-f58a5b46faea" do
  block do
  	node.run_list.remove("recipe[service7a028df1-bdf6-46ae-885e-f58a5b46faea::war]")
  end
  only_if { node.run_list.include?("recipe[service7a028df1-bdf6-46ae-885e-f58a5b46faea::war]") }
end

ruby_block "remove-deactivate-service7a028df1-bdf6-46ae-885e-f58a5b46faea" do
  block do
    node.run_list.remove("recipe[deactivate-service7a028df1-bdf6-46ae-885e-f58a5b46faea]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7a028df1-bdf6-46ae-885e-f58a5b46faea]") }
end
