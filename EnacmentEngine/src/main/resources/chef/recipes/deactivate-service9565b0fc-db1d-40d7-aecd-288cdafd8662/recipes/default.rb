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

ruby_block "remove-service9565b0fc-db1d-40d7-aecd-288cdafd8662" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service9565b0fc-db1d-40d7-aecd-288cdafd8662::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/9565b0fc-db1d-40d7-aecd-288cdafd8662.war]", :immediately
end

ruby_block "remove-service9565b0fc-db1d-40d7-aecd-288cdafd8662" do
  block do
  	node.run_list.remove("recipe[service9565b0fc-db1d-40d7-aecd-288cdafd8662::war]")
  end
  only_if { node.run_list.include?("recipe[service9565b0fc-db1d-40d7-aecd-288cdafd8662::war]") }
end

ruby_block "remove-deactivate-service9565b0fc-db1d-40d7-aecd-288cdafd8662" do
  block do
    node.run_list.remove("recipe[deactivate-service9565b0fc-db1d-40d7-aecd-288cdafd8662]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service9565b0fc-db1d-40d7-aecd-288cdafd8662]") }
end
