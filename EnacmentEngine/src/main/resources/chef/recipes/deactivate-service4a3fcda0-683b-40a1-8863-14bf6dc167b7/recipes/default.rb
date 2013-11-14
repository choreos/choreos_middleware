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

ruby_block "remove-service4a3fcda0-683b-40a1-8863-14bf6dc167b7" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4a3fcda0-683b-40a1-8863-14bf6dc167b7::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4a3fcda0-683b-40a1-8863-14bf6dc167b7.war]", :immediately
end

ruby_block "remove-service4a3fcda0-683b-40a1-8863-14bf6dc167b7" do
  block do
  	node.run_list.remove("recipe[service4a3fcda0-683b-40a1-8863-14bf6dc167b7::war]")
  end
  only_if { node.run_list.include?("recipe[service4a3fcda0-683b-40a1-8863-14bf6dc167b7::war]") }
end

ruby_block "remove-deactivate-service4a3fcda0-683b-40a1-8863-14bf6dc167b7" do
  block do
    node.run_list.remove("recipe[deactivate-service4a3fcda0-683b-40a1-8863-14bf6dc167b7]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4a3fcda0-683b-40a1-8863-14bf6dc167b7]") }
end
