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

ruby_block "remove-service4cbbec11-5750-468a-adf9-4a8cf470bc56" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service4cbbec11-5750-468a-adf9-4a8cf470bc56::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/4cbbec11-5750-468a-adf9-4a8cf470bc56.war]", :immediately
end

ruby_block "remove-service4cbbec11-5750-468a-adf9-4a8cf470bc56" do
  block do
  	node.run_list.remove("recipe[service4cbbec11-5750-468a-adf9-4a8cf470bc56::war]")
  end
  only_if { node.run_list.include?("recipe[service4cbbec11-5750-468a-adf9-4a8cf470bc56::war]") }
end

ruby_block "remove-deactivate-service4cbbec11-5750-468a-adf9-4a8cf470bc56" do
  block do
    node.run_list.remove("recipe[deactivate-service4cbbec11-5750-468a-adf9-4a8cf470bc56]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service4cbbec11-5750-468a-adf9-4a8cf470bc56]") }
end
