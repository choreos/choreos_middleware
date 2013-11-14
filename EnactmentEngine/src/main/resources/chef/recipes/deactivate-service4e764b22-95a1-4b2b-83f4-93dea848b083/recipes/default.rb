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

ruby_block "remove-service4e764b22-95a1-4b2b-83f4-93dea848b083" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4e764b22-95a1-4b2b-83f4-93dea848b083::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4e764b22-95a1-4b2b-83f4-93dea848b083.war]", :immediately
end

ruby_block "remove-service4e764b22-95a1-4b2b-83f4-93dea848b083" do
  block do
  	node.run_list.remove("recipe[service4e764b22-95a1-4b2b-83f4-93dea848b083::war]")
  end
  only_if { node.run_list.include?("recipe[service4e764b22-95a1-4b2b-83f4-93dea848b083::war]") }
end

ruby_block "remove-deactivate-service4e764b22-95a1-4b2b-83f4-93dea848b083" do
  block do
    node.run_list.remove("recipe[deactivate-service4e764b22-95a1-4b2b-83f4-93dea848b083]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4e764b22-95a1-4b2b-83f4-93dea848b083]") }
end
