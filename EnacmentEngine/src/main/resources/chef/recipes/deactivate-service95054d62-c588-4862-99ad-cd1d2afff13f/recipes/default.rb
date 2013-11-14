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

ruby_block "remove-service95054d62-c588-4862-99ad-cd1d2afff13f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service95054d62-c588-4862-99ad-cd1d2afff13f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/95054d62-c588-4862-99ad-cd1d2afff13f.war]", :immediately
end

ruby_block "remove-service95054d62-c588-4862-99ad-cd1d2afff13f" do
  block do
  	node.run_list.remove("recipe[service95054d62-c588-4862-99ad-cd1d2afff13f::war]")
  end
  only_if { node.run_list.include?("recipe[service95054d62-c588-4862-99ad-cd1d2afff13f::war]") }
end

ruby_block "remove-deactivate-service95054d62-c588-4862-99ad-cd1d2afff13f" do
  block do
    node.run_list.remove("recipe[deactivate-service95054d62-c588-4862-99ad-cd1d2afff13f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service95054d62-c588-4862-99ad-cd1d2afff13f]") }
end
