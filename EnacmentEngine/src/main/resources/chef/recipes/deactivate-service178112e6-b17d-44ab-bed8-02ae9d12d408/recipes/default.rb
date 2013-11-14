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

ruby_block "remove-service178112e6-b17d-44ab-bed8-02ae9d12d408" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service178112e6-b17d-44ab-bed8-02ae9d12d408::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/178112e6-b17d-44ab-bed8-02ae9d12d408.war]", :immediately
end

ruby_block "remove-service178112e6-b17d-44ab-bed8-02ae9d12d408" do
  block do
  	node.run_list.remove("recipe[service178112e6-b17d-44ab-bed8-02ae9d12d408::war]")
  end
  only_if { node.run_list.include?("recipe[service178112e6-b17d-44ab-bed8-02ae9d12d408::war]") }
end

ruby_block "remove-deactivate-service178112e6-b17d-44ab-bed8-02ae9d12d408" do
  block do
    node.run_list.remove("recipe[deactivate-service178112e6-b17d-44ab-bed8-02ae9d12d408]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service178112e6-b17d-44ab-bed8-02ae9d12d408]") }
end
