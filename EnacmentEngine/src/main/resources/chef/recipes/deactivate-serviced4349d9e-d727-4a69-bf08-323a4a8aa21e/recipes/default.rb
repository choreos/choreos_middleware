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

ruby_block "remove-serviced4349d9e-d727-4a69-bf08-323a4a8aa21e" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[serviced4349d9e-d727-4a69-bf08-323a4a8aa21e::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/d4349d9e-d727-4a69-bf08-323a4a8aa21e.war]", :immediately
end

ruby_block "remove-serviced4349d9e-d727-4a69-bf08-323a4a8aa21e" do
  block do
  	node.run_list.remove("recipe[serviced4349d9e-d727-4a69-bf08-323a4a8aa21e::war]")
  end
  only_if { node.run_list.include?("recipe[serviced4349d9e-d727-4a69-bf08-323a4a8aa21e::war]") }
end

ruby_block "remove-deactivate-serviced4349d9e-d727-4a69-bf08-323a4a8aa21e" do
  block do
    node.run_list.remove("recipe[deactivate-serviced4349d9e-d727-4a69-bf08-323a4a8aa21e]")
  end
  only_if { node.run_list.include?("recipe[deactivate-serviced4349d9e-d727-4a69-bf08-323a4a8aa21e]") }
end
