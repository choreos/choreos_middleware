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

ruby_block "remove-servicee19f54fa-37f3-4295-990e-b4115dba4391" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee19f54fa-37f3-4295-990e-b4115dba4391::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e19f54fa-37f3-4295-990e-b4115dba4391.war]", :immediately
end

ruby_block "remove-servicee19f54fa-37f3-4295-990e-b4115dba4391" do
  block do
  	node.run_list.remove("recipe[servicee19f54fa-37f3-4295-990e-b4115dba4391::war]")
  end
  only_if { node.run_list.include?("recipe[servicee19f54fa-37f3-4295-990e-b4115dba4391::war]") }
end

ruby_block "remove-deactivate-servicee19f54fa-37f3-4295-990e-b4115dba4391" do
  block do
    node.run_list.remove("recipe[deactivate-servicee19f54fa-37f3-4295-990e-b4115dba4391]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee19f54fa-37f3-4295-990e-b4115dba4391]") }
end
