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

ruby_block "remove-servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/e6de5381-ba38-4111-9dc9-3bd4b74f44d8.war]", :immediately
end

ruby_block "remove-servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8" do
  block do
  	node.run_list.remove("recipe[servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8::war]")
  end
  only_if { node.run_list.include?("recipe[servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8::war]") }
end

ruby_block "remove-deactivate-servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8" do
  block do
    node.run_list.remove("recipe[deactivate-servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicee6de5381-ba38-4111-9dc9-3bd4b74f44d8]") }
end
