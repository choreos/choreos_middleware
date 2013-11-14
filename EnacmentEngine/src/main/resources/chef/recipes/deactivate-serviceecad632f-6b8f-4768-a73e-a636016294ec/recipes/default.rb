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

ruby_block "remove-serviceecad632f-6b8f-4768-a73e-a636016294ec" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviceecad632f-6b8f-4768-a73e-a636016294ec::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/ecad632f-6b8f-4768-a73e-a636016294ec.war]", :immediately
end

ruby_block "remove-serviceecad632f-6b8f-4768-a73e-a636016294ec" do
  block do
  	node.run_list.remove("recipe[serviceecad632f-6b8f-4768-a73e-a636016294ec::war]")
  end
  only_if { node.run_list.include?("recipe[serviceecad632f-6b8f-4768-a73e-a636016294ec::war]") }
end

ruby_block "remove-deactivate-serviceecad632f-6b8f-4768-a73e-a636016294ec" do
  block do
    node.run_list.remove("recipe[deactivate-serviceecad632f-6b8f-4768-a73e-a636016294ec]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviceecad632f-6b8f-4768-a73e-a636016294ec]") }
end
