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

ruby_block "remove-serviceedee2270-2980-4416-88d1-bd67b8577531" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceedee2270-2980-4416-88d1-bd67b8577531::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/edee2270-2980-4416-88d1-bd67b8577531.war]", :immediately
end

ruby_block "remove-serviceedee2270-2980-4416-88d1-bd67b8577531" do
  block do
  	node.run_list.remove("recipe[serviceedee2270-2980-4416-88d1-bd67b8577531::war]")
  end
  only_if { node.run_list.include?("recipe[serviceedee2270-2980-4416-88d1-bd67b8577531::war]") }
end

ruby_block "remove-deactivate-serviceedee2270-2980-4416-88d1-bd67b8577531" do
  block do
    node.run_list.remove("recipe[deactivate-serviceedee2270-2980-4416-88d1-bd67b8577531]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceedee2270-2980-4416-88d1-bd67b8577531]") }
end
