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

ruby_block "remove-service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc.war]", :immediately
end

ruby_block "remove-service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc" do
  block do
  	node.run_list.remove("recipe[service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc::war]")
  end
  only_if { node.run_list.include?("recipe[service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc::war]") }
end

ruby_block "remove-deactivate-service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc" do
  block do
    node.run_list.remove("recipe[deactivate-service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5cfcd59c-5bfb-4b9d-8964-6ed8bb4fe2cc]") }
end
