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

ruby_block "remove-servicee9122571-64a2-4ad4-8cab-46d731a6cfee" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee9122571-64a2-4ad4-8cab-46d731a6cfee::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e9122571-64a2-4ad4-8cab-46d731a6cfee.war]", :immediately
end

ruby_block "remove-servicee9122571-64a2-4ad4-8cab-46d731a6cfee" do
  block do
  	node.run_list.remove("recipe[servicee9122571-64a2-4ad4-8cab-46d731a6cfee::war]")
  end
  only_if { node.run_list.include?("recipe[servicee9122571-64a2-4ad4-8cab-46d731a6cfee::war]") }
end

ruby_block "remove-deactivate-servicee9122571-64a2-4ad4-8cab-46d731a6cfee" do
  block do
    node.run_list.remove("recipe[deactivate-servicee9122571-64a2-4ad4-8cab-46d731a6cfee]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee9122571-64a2-4ad4-8cab-46d731a6cfee]") }
end
