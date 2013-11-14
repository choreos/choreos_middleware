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

ruby_block "remove-service755f2938-fcf4-4ec5-92da-e12329a7de2f" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service755f2938-fcf4-4ec5-92da-e12329a7de2f::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/755f2938-fcf4-4ec5-92da-e12329a7de2f.war]", :immediately
end

ruby_block "remove-service755f2938-fcf4-4ec5-92da-e12329a7de2f" do
  block do
  	node.run_list.remove("recipe[service755f2938-fcf4-4ec5-92da-e12329a7de2f::war]")
  end
  only_if { node.run_list.include?("recipe[service755f2938-fcf4-4ec5-92da-e12329a7de2f::war]") }
end

ruby_block "remove-deactivate-service755f2938-fcf4-4ec5-92da-e12329a7de2f" do
  block do
    node.run_list.remove("recipe[deactivate-service755f2938-fcf4-4ec5-92da-e12329a7de2f]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service755f2938-fcf4-4ec5-92da-e12329a7de2f]") }
end
