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

ruby_block "remove-service057f78a3-c5c5-4166-b26e-02b7e5daabef" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service057f78a3-c5c5-4166-b26e-02b7e5daabef::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/057f78a3-c5c5-4166-b26e-02b7e5daabef.war]", :immediately
end

ruby_block "remove-service057f78a3-c5c5-4166-b26e-02b7e5daabef" do
  block do
  	node.run_list.remove("recipe[service057f78a3-c5c5-4166-b26e-02b7e5daabef::war]")
  end
  only_if { node.run_list.include?("recipe[service057f78a3-c5c5-4166-b26e-02b7e5daabef::war]") }
end

ruby_block "remove-deactivate-service057f78a3-c5c5-4166-b26e-02b7e5daabef" do
  block do
    node.run_list.remove("recipe[deactivate-service057f78a3-c5c5-4166-b26e-02b7e5daabef]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service057f78a3-c5c5-4166-b26e-02b7e5daabef]") }
end
