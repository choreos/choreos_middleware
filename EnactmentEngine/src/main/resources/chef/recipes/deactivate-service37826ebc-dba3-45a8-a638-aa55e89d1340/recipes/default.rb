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

ruby_block "remove-service37826ebc-dba3-45a8-a638-aa55e89d1340" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service37826ebc-dba3-45a8-a638-aa55e89d1340::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/37826ebc-dba3-45a8-a638-aa55e89d1340.war]", :immediately
end

ruby_block "remove-service37826ebc-dba3-45a8-a638-aa55e89d1340" do
  block do
  	node.run_list.remove("recipe[service37826ebc-dba3-45a8-a638-aa55e89d1340::war]")
  end
  only_if { node.run_list.include?("recipe[service37826ebc-dba3-45a8-a638-aa55e89d1340::war]") }
end

ruby_block "remove-deactivate-service37826ebc-dba3-45a8-a638-aa55e89d1340" do
  block do
    node.run_list.remove("recipe[deactivate-service37826ebc-dba3-45a8-a638-aa55e89d1340]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service37826ebc-dba3-45a8-a638-aa55e89d1340]") }
end
