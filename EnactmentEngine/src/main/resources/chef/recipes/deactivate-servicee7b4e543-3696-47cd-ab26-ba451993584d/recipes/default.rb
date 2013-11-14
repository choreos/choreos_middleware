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

ruby_block "remove-servicee7b4e543-3696-47cd-ab26-ba451993584d" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee7b4e543-3696-47cd-ab26-ba451993584d::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e7b4e543-3696-47cd-ab26-ba451993584d.war]", :immediately
end

ruby_block "remove-servicee7b4e543-3696-47cd-ab26-ba451993584d" do
  block do
  	node.run_list.remove("recipe[servicee7b4e543-3696-47cd-ab26-ba451993584d::war]")
  end
  only_if { node.run_list.include?("recipe[servicee7b4e543-3696-47cd-ab26-ba451993584d::war]") }
end

ruby_block "remove-deactivate-servicee7b4e543-3696-47cd-ab26-ba451993584d" do
  block do
    node.run_list.remove("recipe[deactivate-servicee7b4e543-3696-47cd-ab26-ba451993584d]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee7b4e543-3696-47cd-ab26-ba451993584d]") }
end
