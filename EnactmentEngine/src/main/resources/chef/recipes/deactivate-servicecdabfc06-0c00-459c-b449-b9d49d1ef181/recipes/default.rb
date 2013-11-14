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

ruby_block "remove-servicecdabfc06-0c00-459c-b449-b9d49d1ef181" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicecdabfc06-0c00-459c-b449-b9d49d1ef181::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/cdabfc06-0c00-459c-b449-b9d49d1ef181.war]", :immediately
end

ruby_block "remove-servicecdabfc06-0c00-459c-b449-b9d49d1ef181" do
  block do
  	node.run_list.remove("recipe[servicecdabfc06-0c00-459c-b449-b9d49d1ef181::war]")
  end
  only_if { node.run_list.include?("recipe[servicecdabfc06-0c00-459c-b449-b9d49d1ef181::war]") }
end

ruby_block "remove-deactivate-servicecdabfc06-0c00-459c-b449-b9d49d1ef181" do
  block do
    node.run_list.remove("recipe[deactivate-servicecdabfc06-0c00-459c-b449-b9d49d1ef181]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicecdabfc06-0c00-459c-b449-b9d49d1ef181]") }
end
