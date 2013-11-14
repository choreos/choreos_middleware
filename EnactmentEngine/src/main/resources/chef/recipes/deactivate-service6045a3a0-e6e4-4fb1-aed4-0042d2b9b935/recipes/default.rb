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

ruby_block "remove-service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/6045a3a0-e6e4-4fb1-aed4-0042d2b9b935.war]", :immediately
end

ruby_block "remove-service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935" do
  block do
  	node.run_list.remove("recipe[service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935::war]")
  end
  only_if { node.run_list.include?("recipe[service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935::war]") }
end

ruby_block "remove-deactivate-service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935" do
  block do
    node.run_list.remove("recipe[deactivate-service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6045a3a0-e6e4-4fb1-aed4-0042d2b9b935]") }
end
