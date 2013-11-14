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

ruby_block "remove-service5d5e282e-2b08-484b-90a5-66e97bfb1880" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5d5e282e-2b08-484b-90a5-66e97bfb1880::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/5d5e282e-2b08-484b-90a5-66e97bfb1880.war]", :immediately
end

ruby_block "remove-service5d5e282e-2b08-484b-90a5-66e97bfb1880" do
  block do
  	node.run_list.remove("recipe[service5d5e282e-2b08-484b-90a5-66e97bfb1880::war]")
  end
  only_if { node.run_list.include?("recipe[service5d5e282e-2b08-484b-90a5-66e97bfb1880::war]") }
end

ruby_block "remove-deactivate-service5d5e282e-2b08-484b-90a5-66e97bfb1880" do
  block do
    node.run_list.remove("recipe[deactivate-service5d5e282e-2b08-484b-90a5-66e97bfb1880]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5d5e282e-2b08-484b-90a5-66e97bfb1880]") }
end
