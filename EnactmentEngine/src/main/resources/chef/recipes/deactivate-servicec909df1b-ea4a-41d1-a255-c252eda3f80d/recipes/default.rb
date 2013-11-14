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

ruby_block "remove-servicec909df1b-ea4a-41d1-a255-c252eda3f80d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec909df1b-ea4a-41d1-a255-c252eda3f80d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/c909df1b-ea4a-41d1-a255-c252eda3f80d.war]", :immediately
end

ruby_block "remove-servicec909df1b-ea4a-41d1-a255-c252eda3f80d" do
  block do
  	node.run_list.remove("recipe[servicec909df1b-ea4a-41d1-a255-c252eda3f80d::war]")
  end
  only_if { node.run_list.include?("recipe[servicec909df1b-ea4a-41d1-a255-c252eda3f80d::war]") }
end

ruby_block "remove-deactivate-servicec909df1b-ea4a-41d1-a255-c252eda3f80d" do
  block do
    node.run_list.remove("recipe[deactivate-servicec909df1b-ea4a-41d1-a255-c252eda3f80d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec909df1b-ea4a-41d1-a255-c252eda3f80d]") }
end
