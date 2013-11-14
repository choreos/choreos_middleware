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

ruby_block "remove-service7994b326-ec00-4d71-87e5-0dcc614cabe3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service7994b326-ec00-4d71-87e5-0dcc614cabe3::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/7994b326-ec00-4d71-87e5-0dcc614cabe3.war]", :immediately
end

ruby_block "remove-service7994b326-ec00-4d71-87e5-0dcc614cabe3" do
  block do
  	node.run_list.remove("recipe[service7994b326-ec00-4d71-87e5-0dcc614cabe3::war]")
  end
  only_if { node.run_list.include?("recipe[service7994b326-ec00-4d71-87e5-0dcc614cabe3::war]") }
end

ruby_block "remove-deactivate-service7994b326-ec00-4d71-87e5-0dcc614cabe3" do
  block do
    node.run_list.remove("recipe[deactivate-service7994b326-ec00-4d71-87e5-0dcc614cabe3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service7994b326-ec00-4d71-87e5-0dcc614cabe3]") }
end
