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

ruby_block "remove-service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/1f5dcdb3-ea92-4237-8c80-7f758bc59eb9.war]", :immediately
end

ruby_block "remove-service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9" do
  block do
  	node.run_list.remove("recipe[service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9::war]")
  end
  only_if { node.run_list.include?("recipe[service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9::war]") }
end

ruby_block "remove-deactivate-service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9" do
  block do
    node.run_list.remove("recipe[deactivate-service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service1f5dcdb3-ea92-4237-8c80-7f758bc59eb9]") }
end
