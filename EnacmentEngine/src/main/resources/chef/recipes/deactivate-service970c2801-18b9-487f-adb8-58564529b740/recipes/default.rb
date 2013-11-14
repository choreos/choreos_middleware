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

ruby_block "remove-service970c2801-18b9-487f-adb8-58564529b740" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service970c2801-18b9-487f-adb8-58564529b740::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/970c2801-18b9-487f-adb8-58564529b740.war]", :immediately
end

ruby_block "remove-service970c2801-18b9-487f-adb8-58564529b740" do
  block do
  	node.run_list.remove("recipe[service970c2801-18b9-487f-adb8-58564529b740::war]")
  end
  only_if { node.run_list.include?("recipe[service970c2801-18b9-487f-adb8-58564529b740::war]") }
end

ruby_block "remove-deactivate-service970c2801-18b9-487f-adb8-58564529b740" do
  block do
    node.run_list.remove("recipe[deactivate-service970c2801-18b9-487f-adb8-58564529b740]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service970c2801-18b9-487f-adb8-58564529b740]") }
end
