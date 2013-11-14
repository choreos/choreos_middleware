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

ruby_block "remove-service63c8557c-190f-4277-ba82-37847ae745e3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service63c8557c-190f-4277-ba82-37847ae745e3::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/63c8557c-190f-4277-ba82-37847ae745e3.war]", :immediately
end

ruby_block "remove-service63c8557c-190f-4277-ba82-37847ae745e3" do
  block do
  	node.run_list.remove("recipe[service63c8557c-190f-4277-ba82-37847ae745e3::war]")
  end
  only_if { node.run_list.include?("recipe[service63c8557c-190f-4277-ba82-37847ae745e3::war]") }
end

ruby_block "remove-deactivate-service63c8557c-190f-4277-ba82-37847ae745e3" do
  block do
    node.run_list.remove("recipe[deactivate-service63c8557c-190f-4277-ba82-37847ae745e3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service63c8557c-190f-4277-ba82-37847ae745e3]") }
end
